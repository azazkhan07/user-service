package com.novapay.userservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record WalletResponse(
        @Schema(example = "101")
        Long id,
        @Schema(example = "WDGHFIGHDJFG5888")
        String walletNumber,
        @Schema(example = "1")
        Long userId
        ){ }
