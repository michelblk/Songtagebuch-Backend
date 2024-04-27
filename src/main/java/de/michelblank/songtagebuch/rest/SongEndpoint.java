package de.michelblank.songtagebuch.rest;

import de.michelblank.songtagebuch.rest.transferobject.SongTO;
import de.michelblank.songtagebuch.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongEndpoint {
    private final SpotifyService spotifyService;

    @GetMapping("/search")
    public List<SongTO> search(OAuth2AuthenticationToken authentication, @RequestParam final String query) throws IOException {
        return spotifyService.search(authentication, query)
                .stream()
                .map(SongTO::build)
                .toList();
    }
}
