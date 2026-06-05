package com.novapay.userservice.util;



import com.novapay.userservice.entity.User;
import com.novapay.userservice.exception.ResourceNotFoundException;
import com.novapay.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
