package de.michelblank.songtagebuch.rest.transferobject;

import de.michelblank.songtagebuch.database.entity.Song;
import lombok.Builder;
import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Track;

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

    public static SongTO build(final Track track) {
        return SongTO.builder()
                .id(track.getId())
                .interpret(track.getArtists()[0].getName()) // TODO null-safety
                .name(track.getName())
                .album(track.getAlbum().getName())
                .coverUrl(track.getAlbum().getImages()[0].getUrl()) // TODO null-safety
                .previewUrl(track.getPreviewUrl())
                .build();
    }

    public static SongTO build(final Song song) {
        return SongTO.builder()
                .id(song.getId().toString())
                .interpret(song.getInterpret())
                .name(song.getName())
                .album(song.getAlbum())
                .coverUrl(song.getCoverUrl())
                .previewUrl(song.getPreviewUrl())
                .build();
    }
}
