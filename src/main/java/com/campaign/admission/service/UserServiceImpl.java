package com.campaign.admission.service;

import com.campaign.admission.domain.User;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.repository.RoleRepository;
import com.campaign.admission.repository.UserRepository;
import com.campaign.admission.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
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

        return mapper.mapDomainFromEntity(user);
    }

    @Transactional
    @Override
    public User register(User user) {
        ofNullable(userRepository.findByEmail(user.getEmail())).ifPresent(user1 -> {
            throw new ServiceRuntimeException("Registration exception! User already exists!");
        });
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRole().setId(roleRepository.findByRole(user.getRole().toString()).getId());

        return mapper.mapDomainFromEntity(userRepository.save(mapper.mapEntityFromDomain(user)));
    }
}
