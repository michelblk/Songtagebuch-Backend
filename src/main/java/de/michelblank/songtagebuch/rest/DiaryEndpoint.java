package de.michelblank.songtagebuch.rest;

import de.michelblank.songtagebuch.database.service.DiaryEntryService;
import de.michelblank.songtagebuch.rest.transferobject.DiaryEntryTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userid}/diary")
@RequiredArgsConstructor
public class DiaryEndpoint {
    private final DiaryEntryService diaryEntryService;

    @GetMapping("/{date}")
    public List<DiaryEntryTO> getDiaryEntriesOfDay(@PathVariable final UUID userid,
                                                   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date date) {
        return diaryEntryService.getEntriesByUserOfDay(userid, date).stream().map(DiaryEntryTO::build).toList();
    }
}
