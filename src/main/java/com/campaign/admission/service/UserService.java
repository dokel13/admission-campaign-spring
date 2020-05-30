package com.campaign.admission.service;

import com.campaign.admission.domain.User;

public interface UserService {

    User login(User user);

    User register(User user);
}
