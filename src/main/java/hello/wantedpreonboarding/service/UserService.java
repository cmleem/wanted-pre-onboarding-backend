package hello.wantedpreonboarding.service;

import hello.wantedpreonboarding.dto.UserDto;
import hello.wantedpreonboarding.entity.User;
import hello.wantedpreonboarding.mapper.UserMapper;
import hello.wantedpreonboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUserByUsername(String username) {
        User user = getUser(username);
        return UserMapper.toDto(user);
    }

    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper::toDto);
    }

    public void deleteUserByUsername(String username) {
        User user = getUser(username);
        userRepository.delete(user);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User with name " + username + " not found"));
    }
}
