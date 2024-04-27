package de.michelblank.songtagebuch.rest.transferobject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTO {
    private String username;
    private String name;
}
