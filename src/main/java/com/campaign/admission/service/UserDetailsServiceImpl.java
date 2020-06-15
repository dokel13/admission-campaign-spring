package com.campaign.admission.service;

import com.campaign.admission.domain.User;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.repository.UserRepository;
import com.campaign.admission.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            String message = "Login exception! User doesn`t exist!";
            log.warn(message);
            throw new UsernameNotFoundException(message);
        }

        User user1 = mapper.mapDomainFromEntity(user);

        return user1;
    }
}
