package com.novapay.userservice.dto.response;

public record UserContactResponse(
        Long id,
        String email,
        String phoneNumber,
        String fullName
) { }
