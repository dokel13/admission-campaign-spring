package com.campaign.admission.service;

import com.campaign.admission.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(User user);
}
