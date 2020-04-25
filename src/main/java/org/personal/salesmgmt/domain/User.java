package org.personal.salesmgmt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String username;

    private String password;

    private String role;
}
