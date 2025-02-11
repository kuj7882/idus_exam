package com.example.idus.user;

//import com.example.idus.emailverify.EmailVerifyService;
import com.example.idus.user.model.User;
import com.example.idus.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final EmailVerifyService emailVerifyService;


    public UserDto.SignupResponse signup(UserDto.SignupRequest dto) {

        User user = userRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));

        return UserDto.SignupResponse.from(user);
    }
    // 5번 로직
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);

        if (result.isPresent()) {
            // 7번 로직
            User user = result.get();
            return user;
        }

        return null;
    }


}
