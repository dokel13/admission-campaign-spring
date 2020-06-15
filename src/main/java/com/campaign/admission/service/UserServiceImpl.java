package com.campaign.admission.service;

import com.campaign.admission.domain.User;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import com.campaign.admission.repository.RoleRepository;
import com.campaign.admission.repository.UserRepository;
import com.campaign.admission.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final Mapper<User, UserEntity> mapper;

    @Override
    public User login(User enteringUser) {
        User existingUser = ofNullable(mapper.mapDomainFromEntity(userRepository
                .findByEmail(enteringUser.getEmail())))
                .orElseThrow(() ->
                        new ServiceRuntimeException("Login exception! User doesn`t exist!"));
        validatePassword(enteringUser, existingUser);

        return existingUser;
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

    private void validatePassword(User enteringUser, User existingUser) {
        if (!encoder.matches(enteringUser.getPassword(), existingUser.getPassword())) {
            throw new UserValidatorRuntimeException("Wrong password!");
        }
    }
}
