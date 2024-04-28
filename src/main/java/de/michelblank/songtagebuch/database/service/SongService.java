package de.michelblank.songtagebuch.database.service;

import de.michelblank.songtagebuch.database.entity.Song;
import de.michelblank.songtagebuch.database.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public Optional<Song> findById(final UUID id) {
        return songRepository.findById(id);
    }
}
