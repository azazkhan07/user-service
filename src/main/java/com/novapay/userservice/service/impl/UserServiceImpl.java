package com.novapay.userservice.service.impl;

import com.novapay.userservice.client.WalletClient;
import com.novapay.userservice.dto.request.CreateWalletRequest;
import com.novapay.userservice.dto.request.KycRequest;
import com.novapay.userservice.dto.request.UpdateUserRequest;
import com.novapay.userservice.dto.request.UserRequest;
import com.novapay.userservice.dto.response.UserAuthResponse;
import com.novapay.userservice.dto.response.UserResponse;
import com.novapay.userservice.entity.User;
import com.novapay.userservice.entity.enums.Role;
import com.novapay.userservice.entity.enums.UserStatus;
import com.novapay.userservice.exception.InvalidKycStateException;
import com.novapay.userservice.exception.ResourceNotFoundException;
import com.novapay.userservice.exception.UserAlreadyExistsException;
import com.novapay.userservice.mapper.UserMapper;
import com.novapay.userservice.repository.UserRepository;
import com.novapay.userservice.service.UserService;
import com.novapay.userservice.util.UserHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserHelper userHelper;
    private final PasswordEncoder passwordEncoder;
    private final WalletClient walletClient;


    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email " + userRequest.getEmail());
        }
        User user = userMapper.toUserEntity(userRequest);
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setStatus(UserStatus.CREATED);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saveUser = userRepository.save(user);
        walletClient.createWallet(new CreateWalletRequest(saveUser.getId()));
        LOGGER.info("User created with id {} and email {} ", user.getId(), user.getEmail());
        return userMapper.toResponseDTO(saveUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(Long userId) {
        User user = userHelper.findUser(userId);
        LOGGER.info("Getting User by id {} ", userId);
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User user = userHelper.findUser(userId);
        userMapper.updateUserFromDto(updateUserRequest, user);
        LOGGER.info("User updated with id {} ", userId);
        return userMapper.toResponseDTO(user);
    }

    @Override
    public void submitKyc(Long userId, KycRequest kycRequest) {
        LOGGER.info("KYC submission started for user {}", userId);
        User user = userHelper.findUser(userId);
        if (user.isKycVerified()) {
            throw new InvalidKycStateException("KYC has already been verified");
        }
        userMapper.updateKycFromDto(kycRequest, user);

        user.setStatus(UserStatus.PENDING_KYC);
        user.setKycVerified(false);
        user.setKycSubmittedAt(LocalDateTime.now());
        LOGGER.info("KYC submission successfully for user {}", userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean getKycStatus(Long userId) {
        User user = userHelper.findUser(userId);
        LOGGER.info("KYC status for user {}", userId);
        return user.isKycVerified();
    }

    @Override
    public void verifyKyc(Long userId) {
        LOGGER.info("Verifying KYC status for user {}", userId);
        User user = userHelper.findUser(userId);
        if (user.getStatus() != UserStatus.PENDING_KYC) {
            throw new InvalidKycStateException("KYC not submitted yet");
        }
        user.setKycVerified(true);
        user.setStatus(UserStatus.ACTIVE);
        LOGGER.info("KYC verified successfully for user {}", userId);
    }

    @Override
    public UserAuthResponse getUserForAuth(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        return new UserAuthResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                user.isEnabled());
    }
}