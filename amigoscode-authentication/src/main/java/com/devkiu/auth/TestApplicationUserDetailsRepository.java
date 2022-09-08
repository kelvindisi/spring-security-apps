package com.devkiu.auth;

import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.devkiu.security.ApplicationUserRole.*;

@Repository("test")
public class TestApplicationUserDetailsRepository implements ApplicationUserDetailsRepository {
    private final PasswordEncoder passwordEncoder;

    public TestApplicationUserDetailsRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUserDetails> getUserDetailsByUsername(String username) {
        return users().stream().filter(userDetails -> username.equals(userDetails.getUsername()))
                .findFirst();
    }

    private List<ApplicationUserDetails> users() {
        return Lists.newArrayList(
                new ApplicationUserDetails(
                        "student",
                        passwordEncoder.encode("password"),
                        STUDENT.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUserDetails(
                        "admin",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUserDetails(
                        "trainee",
                        passwordEncoder.encode("password"),
                        ADMIN_TRAINEE.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
