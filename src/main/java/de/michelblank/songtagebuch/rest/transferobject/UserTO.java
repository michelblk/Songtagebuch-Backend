package de.michelblank.songtagebuch.rest.transferobject;

import de.michelblank.songtagebuch.database.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserTO {
    private UUID id;
    private String name;

    public static UserTO build(final User user) {
        return UserTO.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
