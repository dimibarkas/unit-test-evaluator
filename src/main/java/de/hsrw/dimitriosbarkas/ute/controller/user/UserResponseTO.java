package de.hsrw.dimitriosbarkas.ute.controller.user;

import de.hsrw.dimitriosbarkas.ute.persistence.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponseTO {

    private UUID id;

    private LocalDateTime createdAt;

    public UserResponseTO(User user) {
        this.id = user.getId();
        this.createdAt = user.getCreatedAt();
    }

}
