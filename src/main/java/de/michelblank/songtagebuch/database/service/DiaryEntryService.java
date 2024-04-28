package de.michelblank.songtagebuch.database.service;

import de.michelblank.songtagebuch.database.entity.DiaryEntry;
import de.michelblank.songtagebuch.database.repository.DiaryEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiaryEntryService {
    private final DiaryEntryRepository repository;

    public Optional<DiaryEntry> findById(final UUID id) {
        return repository.findById(id);
    }

    public List<DiaryEntry> getEntriesByUserBetweenDates(final UUID userid, final Date from, final Date until) {
        final Calendar untilCalendar = new GregorianCalendar(); // count whole until day
        untilCalendar.setTime(until);
        untilCalendar.add(Calendar.DAY_OF_MONTH, 1);
        untilCalendar.add(Calendar.MILLISECOND, -1);

        return repository.findAllByUserIdAndReferenceDateBetweenOrderByReferenceDateDesc(userid, from, untilCalendar.getTime());
    }

    public DiaryEntry putEntry(final DiaryEntry diaryEntry) {
        return repository.save(diaryEntry);
    }

    public DiaryEntry updateEntry(final DiaryEntry updatedEntry) {
        final DiaryEntry originalEntry = findById(updatedEntry.getId()).orElseThrow(EntityNotFoundException::new);

        originalEntry.setReferenceDate(updatedEntry.getReferenceDate());
        originalEntry.setModificationDate(updatedEntry.getModificationDate());
        originalEntry.setSongs(updatedEntry.getSongs()); // TODO verify if this works and is good
        originalEntry.setEntry(updatedEntry.getEntry());

        return repository.save(originalEntry);
    }
}
