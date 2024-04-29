package de.michelblank.songtagebuch.rest.config;

import de.michelblank.songtagebuch.database.entity.User;
import de.michelblank.songtagebuch.database.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            final User user = createUserIfMissing((OAuth2AuthenticationToken) authentication);
            request.getSession().setAttribute("uid", user.getId());
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private User createUserIfMissing(final OAuth2AuthenticationToken authentication) {
        final OAuth2User principal = authentication.getPrincipal();

        final String idpId = principal.getName();
        final String name = principal.getAttribute("display_name");
        final String provider = authentication.getAuthorizedClientRegistrationId();

        return userService.findByIdentityProviderAndIdpId(provider, idpId)
                .orElseGet(() -> userService.putUser(User.builder().identityProvider(provider).idpId(idpId).name(name).build()));
    }
}
