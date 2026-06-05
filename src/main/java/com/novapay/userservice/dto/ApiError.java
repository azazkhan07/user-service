package com.novapay.userservice.dto;

import java.time.LocalDateTime;

public record ApiError(int status,
                       String error,
                       String message,
                       LocalDateTime timestamp) {
}
