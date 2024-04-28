package de.michelblank.songtagebuch.service;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public List<Track> search(final OAuth2AuthenticationToken authentication, final String query) throws IOException {
        try {
            final Track[] foundTracks = buildSpotifyApi(authentication)
                    .searchTracks(query)
                    .market(CountryCode.DE)
                    .limit(10)
                    .build()
                    .execute()
                    .getItems();
            return Arrays.asList(foundTracks);
        } catch (final IOException | SpotifyWebApiException | ParseException e) {
            throw new IOException("Failed to search for track using query <" + query + ">", e);
        }
    }

    public Optional<Track> findByTrackId(final OAuth2AuthenticationToken authentication, final String id) throws IOException {
        try {
            return Optional.ofNullable(
                    buildSpotifyApi(authentication)
                            .getTrack(id)
                            .build()
                            .execute()
            );
        } catch (final IOException | SpotifyWebApiException | ParseException e) {
            throw new IOException("Failed to find track with id <" + id + ">", e);
        }
    }

    private SpotifyApi buildSpotifyApi(final OAuth2AuthenticationToken authentication) {
        final String accessToken = oAuth2AuthorizedClientService
                .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName())
                .getAccessToken()
                .getTokenValue();

        return SpotifyApi.builder()
                .setAccessToken(accessToken)
                .build();
    }
}
