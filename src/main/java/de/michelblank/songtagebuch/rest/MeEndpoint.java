package de.michelblank.songtagebuch.rest;

import de.michelblank.songtagebuch.database.service.UserService;
import de.michelblank.songtagebuch.rest.transferobject.UserTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class MeEndpoint {
    private final UserService userService;

    @GetMapping
    public UserTO getUser(final HttpSession session) {
        return userService.findById((UUID) session.getAttribute("uid"))
                .map(UserTO::build)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }
}
