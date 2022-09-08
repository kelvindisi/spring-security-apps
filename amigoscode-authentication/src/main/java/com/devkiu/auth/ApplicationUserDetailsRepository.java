package com.devkiu.auth;

import java.util.Optional;

public interface ApplicationUserDetailsRepository {
    Optional<ApplicationUserDetails> getUserDetailsByUsername(String username);
}
