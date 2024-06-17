package miu.edu.cs.asd.deliverysystem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    //different levels of authorization
    ADMIN_WRITE("admin:write"),
    ADMIN_READ("admin:read"),
    MEMBER_WRITE("member:write"),
    MEMBER_READ("member:read");

    @Getter
    private final String permission;
}
