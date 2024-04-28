package de.michelblank.songtagebuch.database.entity;

import de.michelblank.songtagebuch.database.service.SongService;
import de.michelblank.songtagebuch.rest.transferobject.DiaryEntryTO;
import de.michelblank.songtagebuch.service.SpotifyService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "diary_songs", joinColumns = @JoinColumn(name = "diaryEntryId"), inverseJoinColumns = @JoinColumn(name = "songId"))
    private List<Song> songs;
    private String entry;

    public static DiaryEntry build(final SongService songService, final SpotifyService spotifyService,
                                   final OAuth2AuthenticationToken authentication,
                                   final User user, final DiaryEntryTO entry) {
        return DiaryEntry.builder()
                .id(entry.getId() != null ? UUID.fromString(entry.getId()) : null)
                .referenceDate(entry.getReferenceDate())
                .modificationDate(entry.getModificationDate())
                .user(user)
                .songs(entry.getSongs()
                        .stream()
                        .map(s -> {
                            final Optional<Song> dbSong = songService.findByIdOrSpotifyId(s.getId() != null ? UUID.fromString(s.getId()) : null, s.getSpotifyId());
                            if (dbSong.isPresent()) {
                                return dbSong.get();
                            }

                            try {
                                final Optional<Track> track = spotifyService.findByTrackId(authentication, s.getSpotifyId());
                                if (track.isPresent()) {
                                    return Song.build(track.get());
                                }
                            } catch (final IOException e) {
                                return null;
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
                .entry(entry.getEntry())
                .build();
    }
}
