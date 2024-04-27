package de.michelblank.songtagebuch.database.repository;

import de.michelblank.songtagebuch.database.entity.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SongRepository extends CrudRepository<Song, UUID> {

}
