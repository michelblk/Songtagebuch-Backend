package de.michelblank.songtagebuch.database.service;

import de.michelblank.songtagebuch.database.entity.DiaryEntry;
import de.michelblank.songtagebuch.database.repository.DiaryEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiaryEntryService {
    private final DiaryEntryRepository repository;

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
}
