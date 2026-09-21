package com.novapay.userservice.client;

import com.novapay.userservice.config.InternalServiceFeignConfig;
import com.novapay.userservice.dto.request.CreateWalletRequest;
import com.novapay.userservice.dto.response.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-service", configuration = InternalServiceFeignConfig.class)
public interface WalletClient {

    @PostMapping("/api/v1/wallets")
    WalletResponse createWallet(@RequestBody CreateWalletRequest walletRequest);
}
