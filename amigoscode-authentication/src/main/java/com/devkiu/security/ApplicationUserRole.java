package com.devkiu.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.devkiu.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(
            COURSE_READ,
            COURSE_WRITE,
            STUDENT_READ,
            STUDENT_WRITE
    )),
    ADMIN_TRAINEE(Sets.newHashSet(
            COURSE_READ,
            STUDENT_READ
    )),
    STUDENT(
            Sets.newHashSet(
                    STUDENT_READ
            )
    );

    private final HashSet<ApplicationUserPermission> permissions;

    ApplicationUserRole(HashSet<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<? extends GrantedAuthority> getGrantedAuthority() {
        Set<GrantedAuthority> authorities = permissions.stream()
                .map(permission-> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
