package de.michelblank.songtagebuch.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
public class DiaryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private Date referenceDate;
    @UpdateTimestamp
    private Date modificationDate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "diary_songs", joinColumns = @JoinColumn(name = "diaryEntryId"), inverseJoinColumns = @JoinColumn(name = "songId"))
    private List<Song> songs;
    private String entry;
}
