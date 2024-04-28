package de.michelblank.songtagebuch.rest;

import de.michelblank.songtagebuch.database.entity.DiaryEntry;
import de.michelblank.songtagebuch.database.entity.User;
import de.michelblank.songtagebuch.database.service.DiaryEntryService;
import de.michelblank.songtagebuch.database.service.SongService;
import de.michelblank.songtagebuch.database.service.UserService;
import de.michelblank.songtagebuch.rest.transferobject.DiaryEntryTO;
import de.michelblank.songtagebuch.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users/{userid}/diary")
@RequiredArgsConstructor
public class DiaryEndpoint {
    private final DiaryEntryService diaryEntryService;
    private final UserService userService;
    private final SongService songService;
    private final SpotifyService spotifyService;

    @GetMapping()
    public List<DiaryEntryTO> getDiaryEntriesOfDays(@PathVariable final UUID userid,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date from,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date until) {
        return diaryEntryService.getEntriesByUserBetweenDates(userid, from, until).stream().map(DiaryEntryTO::build).toList();
    }

    @PutMapping
    public ResponseEntity<DiaryEntryTO> addDiaryEntry(OAuth2AuthenticationToken authentication,
                                                      @PathVariable final UUID userid, @RequestBody DiaryEntryTO entry) {
        final Optional<User> user = userService.findById(userid);

        return user
                .map(value -> ResponseEntity.ok(
                        DiaryEntryTO.build(diaryEntryService.putEntry(DiaryEntry.build(
                                songService, spotifyService, authentication, value, entry
                        )))
                ))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @PostMapping("/{diaryEntryId}")
    public ResponseEntity<DiaryEntryTO> changeDiaryEntry(OAuth2AuthenticationToken authentication,
                                                         @PathVariable final UUID userid, @PathVariable final UUID diaryEntryId,
                                                         @RequestBody DiaryEntryTO entry) {
        final Optional<User> user = userService.findById(userid);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (entry.getId() == null || !Objects.equals(diaryEntryId, UUID.fromString(entry.getId()))) {
            return ResponseEntity.badRequest().build();
        }

        final DiaryEntry updatedEntry = diaryEntryService.updateEntry(DiaryEntry.build(
                songService, spotifyService, authentication, user.get(), entry
        ));

        return ResponseEntity.ok(DiaryEntryTO.build(updatedEntry));
    }
}
