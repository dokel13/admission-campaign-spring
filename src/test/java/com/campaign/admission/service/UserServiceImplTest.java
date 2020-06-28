package com.campaign.admission.service;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import com.campaign.admission.entity.RoleEntity;
import com.campaign.admission.entity.UserEntity;
import com.campaign.admission.exception.ServiceRuntimeException;
import com.campaign.admission.repository.RoleRepository;
import com.campaign.admission.repository.UserRepository;
import com.campaign.admission.service.mapper.UserMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.campaign.admission.domain.User.builder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Rule
    public ExpectedException expectedException = none();

    private final User enteringUser = builder()
            .name("name")
            .surname("surname")
            .email("email@mail.ua")
            .password("password")
            .role(Role.STUDENT)
            .build();

    private final RoleEntity roleEntity = new RoleEntity(1, "STUDENT");

    private final UserEntity existingUser = new UserEntity(
            "email@mail.ua",
            "password",
            "name",
            "surname",
            roleEntity);

    @Test
    public void loadUserByUsernameShouldReturnUser() {
        when(userRepository.findByEmail(enteringUser.getEmail())).thenReturn(existingUser);
        when(userMapper.mapDomainFromEntity(existingUser)).thenReturn(enteringUser);
        assertThat(userService.loadUserByUsername(enteringUser.getEmail()), is(enteringUser));

        verify(userRepository).findByEmail(enteringUser.getEmail());
        verify(userMapper).mapDomainFromEntity(existingUser);
    }

    @Test
    public void loadUserByUsernameShouldThrowException() {
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage("Login exception! User doesn`t exist!");
        userService.loadUserByUsername(enteringUser.getEmail());

        verify(userRepository).findByEmail(enteringUser.getEmail());
    }

    @Test
    public void registerShouldReturnUser() {
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(roleRepository.findByRole(roleEntity.getRole())).thenReturn(roleEntity);
        when(userMapper.mapDomainFromEntity(existingUser)).thenReturn(enteringUser);
        when(userMapper.mapEntityFromDomain(enteringUser, roleEntity)).thenReturn(existingUser);
        when(encoder.encode(enteringUser.getPassword())).thenReturn(enteringUser.getPassword());
        assertThat(userService.register(enteringUser), is(enteringUser));

        verify(userRepository).findByEmail(enteringUser.getEmail());
        verify(userRepository).save(any(UserEntity.class));
        verify(roleRepository).findByRole(roleEntity.getRole());
        verify(userMapper).mapDomainFromEntity(existingUser);
        verify(userMapper).mapEntityFromDomain(enteringUser, roleEntity);
        verify(encoder).encode(enteringUser.getPassword());

    }

    @Test
    public void registerShouldThrowException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Registration exception! User already exists!");
        when(userRepository.findByEmail(enteringUser.getEmail())).thenReturn(existingUser);
        userService.register(enteringUser);

        verify(userRepository).findByEmail(enteringUser.getEmail());
    }
}
