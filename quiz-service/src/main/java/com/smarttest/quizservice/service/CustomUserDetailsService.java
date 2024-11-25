package com.smarttest.quizservice.service;

import com.smarttest.quizservice.dao.entities.User;
import com.smarttest.quizservice.dao.repository.UserRepository;
import com.smarttest.quizservice.dto.user.SecUser;
import com.smarttest.quizservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(userName);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
        return userMapper.toSecUser(user.get());
    }
}
