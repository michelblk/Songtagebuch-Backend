package de.michelblank.songtagebuch.database.service;

import de.michelblank.songtagebuch.database.entity.Song;
import de.michelblank.songtagebuch.database.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public Optional<Song> findByIdOrSpotifyId(final UUID id, final String spotifyId) {
        return songRepository.findByIdOrSpotifyId(id, spotifyId);
    }

    public List<Song> search(final String query) {
        return songRepository.findAllByNameContainingOrInterpretContaining(query, query);
    }
}
