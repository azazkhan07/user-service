package com.novapay.userservice.service;

import com.novapay.userservice.dto.request.KycRequest;
import com.novapay.userservice.dto.request.UpdateUserRequest;
import com.novapay.userservice.dto.request.UserRequest;
import com.novapay.userservice.dto.response.UserAuthResponse;
import com.novapay.userservice.dto.response.UserContactResponse;
import com.novapay.userservice.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long userId);

    UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest);

    void submitKyc(Long userId, KycRequest kycRequest);

    Boolean getKycStatus(Long userId);

    void verifyKyc(Long  userId);

    UserAuthResponse getUserForAuth(String email);

    UserContactResponse getUserContact(Long userId);

}
