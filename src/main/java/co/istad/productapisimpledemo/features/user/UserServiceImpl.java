package co.istad.productapisimpledemo.features.user;

import co.istad.productapisimpledemo.features.user.dto.CreateUserRequest;
import co.istad.productapisimpledemo.features.user.dto.UserResponse;
import co.istad.productapisimpledemo.features.profile.Profile;
import co.istad.productapisimpledemo.features.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        var user = userMapper.toUser(request);
        var profile = new Profile();

        profile.setBio(request.bio());
        profile.setProfileUrl(request.profileUrl());
         // linked profile to user
        profile.setUser(user);
        user.setProfile(profile);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUserByKeycloakId(String keycloakId) {
        return userMapper.toUserResponse(userRepository.findByKeycloakId(keycloakId).orElseThrow(
                ()-> new NoSuchElementException("user not found with id: " + keycloakId)
        ));
    }
}
