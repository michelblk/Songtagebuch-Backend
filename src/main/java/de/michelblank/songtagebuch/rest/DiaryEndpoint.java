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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userid}/diary")
@RequiredArgsConstructor
public class DiaryEndpoint {
    private final DiaryEntryService diaryEntryService;
    private final UserService userService;
    private final SongService songService;
    private final SpotifyService spotifyService;

    @GetMapping("/{date}")
    public List<DiaryEntryTO> getDiaryEntriesOfDay(@PathVariable final UUID userid,
                                                   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date date) {
        return diaryEntryService.getEntriesByUserOfDay(userid, date).stream().map(DiaryEntryTO::build).toList();
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
}
