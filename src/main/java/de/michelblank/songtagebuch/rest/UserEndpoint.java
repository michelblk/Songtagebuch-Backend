package de.michelblank.songtagebuch.rest;

import de.michelblank.songtagebuch.rest.transferobject.UserTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserEndpoint {
    @GetMapping
    public UserTO getUser(@AuthenticationPrincipal OAuth2User principal) {
        return UserTO.builder()
                .username(principal.getAttribute("id"))
                .name(principal.getAttribute("display_name"))
                .build();
    }
}
