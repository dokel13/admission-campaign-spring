package com.campaign.admission.service;

import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import com.campaign.admission.repository.UserRepository;
import com.campaign.admission.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.of;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    public User login(User enteringUser) {
        User existingUser = of(mapper.mapUserFromEntity(userRepository
                .findByEmail(enteringUser.getEmail())))
                .orElseThrow(() ->
                        new ServiceRuntimeException("Login exception! User doesn`t exist!"));
        validatePassword(enteringUser, existingUser);

        return existingUser;
    }

    @Transactional
    @Override
    public User register(User user) {
        of(userRepository.findByEmail(user.getEmail())).ifPresent(user1 -> {
            throw new ServiceRuntimeException("Registration exception! User already exists!");
        });
        user.setPassword(encoder.encode(user.getPassword()));

        return mapper.mapUserFromEntity(userRepository.save(mapper.mapEntityFromUser(user)));
    }

    private void validatePassword(User enteringUser, User existingUser) {
        if (!(encoder.encode(enteringUser.getPassword()).equals(existingUser.getPassword()))) {
            throw new UserValidatorRuntimeException("Wrong password!");
        }
    }
}
