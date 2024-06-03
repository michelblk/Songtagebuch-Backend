package de.michelblank.songtagebuch.database.entity;

import de.michelblank.songtagebuch.rest.transferobject.SongTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"interpret", "name", "album"}))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {
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
    @ElementCollection
    @CollectionTable(name = "song_external_url", joinColumns = {@JoinColumn(name = "song_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "provider")
    @Column(name = "url")
    private Map<String, String> externalUrls = new HashMap<>();

    public static Song build(final SongTO songTO, final String spotifyId) {
        return Song.builder()
                .id(UUID.fromString(songTO.getId()))
                .interpret(songTO.getInterpret())
                .name(songTO.getName())
                .album(songTO.getAlbum())
                .spotifyId(spotifyId)
                .coverUrl(songTO.getCoverUrl())
                .previewUrl(songTO.getPreviewUrl())
                .externalUrls(songTO.getExternalUrls())
                .build();
    }

    public static Song build(final Track track) {
        return Song.builder()
                .id(null)
                .interpret(track.getArtists()[0].getName()) // TODO
                .name(track.getName())
                .album(track.getAlbum().getName())
                .spotifyId(track.getId())
                .coverUrl(track.getAlbum().getImages()[0].getUrl()) // TODO
                .previewUrl(track.getPreviewUrl())
                .externalUrls(track.getExternalUrls().getExternalUrls()) // TODO
                .build();
    }
}
