package de.michelblank.songtagebuch.database.repository;

import de.michelblank.songtagebuch.database.entity.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SongRepository extends CrudRepository<Song, UUID> {
    List<Song> findAllByNameContainingOrInterpretContaining(final String name, final String interpret);
}
