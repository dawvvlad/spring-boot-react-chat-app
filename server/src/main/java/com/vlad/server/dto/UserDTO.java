package com.vlad.server.dto;

import com.vlad.server.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String fullName;
    private Boolean status;

    public UserDTO() {}
    public UserDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getName() + " " + user.getSurname();
        this.status = user.getStatus();
    }
}
