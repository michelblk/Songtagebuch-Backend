package de.michelblank.songtagebuch.database.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"interpret", "name", "album"}))
public class DiarySong {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String interpret;
    @Column(nullable = false)
    private String name;
    private String album;
    @Column(nullable = false, unique = true)
    private String spotifyId;
    private String coverUrl;
    private String previewUrl;
}
