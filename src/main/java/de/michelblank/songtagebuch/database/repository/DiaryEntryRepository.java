package de.michelblank.songtagebuch.database.repository;

import de.michelblank.songtagebuch.database.entity.DiaryEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DiaryEntryRepository extends CrudRepository<DiaryEntry, UUID> {
    List<DiaryEntry> findAllByUserIdAndReferenceDateBetween(final UUID userid, final Date startDate, final Date endDate);
}
