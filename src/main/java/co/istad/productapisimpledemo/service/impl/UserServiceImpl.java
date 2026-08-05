package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.user.CreateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.entity.Profile;
import co.istad.productapisimpledemo.entity.User;
import co.istad.productapisimpledemo.mapper.UserMapper;
import co.istad.productapisimpledemo.repository.ProfileRepository;
import co.istad.productapisimpledemo.repository.UserRepository;
import co.istad.productapisimpledemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Create user from client: {}", request);
        User user = userMapper.toUser(request);
        Profile profile = new Profile();

        profile.setProfileUrl(request.profileUrl());
        profile.setGender("Female");
        profile.setBio("ISTAD"+ request.bio());
         // linked profile to user
        profile.setUser(user);
        user.setProfile(profile);
        log.info("Profile is : {}", profile);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Getting all the user is triggered ");
        return userRepository.findAll()
                .stream().map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUserByKeycloakId(String keycloakId) {
        log.info("Get user by keycloakId: {}", keycloakId);
        return userMapper.toUserResponse(userRepository.findByKeycloakId(keycloakId).orElseThrow(
                ()-> new NoSuchElementException("user not found with id: " + keycloakId)
        ));
    }
}
