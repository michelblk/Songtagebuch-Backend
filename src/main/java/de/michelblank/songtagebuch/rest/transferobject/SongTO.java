package de.michelblank.songtagebuch.rest.transferobject;

import de.michelblank.songtagebuch.database.entity.Song;
import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Map;

@Data
@Builder
public class SongTO {
    private final String id;
    private final String interpret;
    private final String name;
    private final String album;
    private String spotifyId;
    private final String coverUrl;
    private final String previewUrl;
    private final Map<String, String> externalUrls;

    public static SongTO build(final Track track) {
        return SongTO.builder()
                .id(null)
                .interpret(track.getArtists()[0].getName()) // TODO null-safety
                .name(track.getName())
                .album(track.getAlbum().getName())
                .spotifyId(track.getId())
                .coverUrl(track.getAlbum().getImages()[0].getUrl()) // TODO null-safety
                .previewUrl(track.getPreviewUrl())
                .externalUrls(track.getExternalUrls().getExternalUrls()) // TODO null-safety
                .build();
    }

    public static SongTO build(final Song song) {
        return SongTO.builder()
                .id(song.getId().toString())
                .interpret(song.getInterpret())
                .name(song.getName())
                .album(song.getAlbum())
                .spotifyId(song.getSpotifyId())
                .coverUrl(song.getCoverUrl())
                .previewUrl(song.getPreviewUrl())
                .externalUrls(song.getExternalUrls())
                .build();
    }
}
