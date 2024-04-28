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

    public List<DiaryEntry> getEntriesByUserOfDay(final UUID userid, final Date date) {
        final Calendar midnightCalendar = new GregorianCalendar();
        midnightCalendar.setTime(date);
        midnightCalendar.set(Calendar.HOUR, 0);
        midnightCalendar.set(Calendar.MINUTE, 0);
        midnightCalendar.set(Calendar.SECOND, 0);
        midnightCalendar.set(Calendar.MILLISECOND, 0);
        final Calendar endOfdayCalendar = (Calendar) midnightCalendar.clone();
        endOfdayCalendar.add(Calendar.DAY_OF_MONTH, 1);
        endOfdayCalendar.add(Calendar.MILLISECOND, -1);

        return repository.findAllByUserIdAndReferenceDateBetween(userid, midnightCalendar.getTime(), endOfdayCalendar.getTime());
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
