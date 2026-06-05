package com.novapay.userservice.mapper;


import com.novapay.userservice.dto.request.KycRequest;
import com.novapay.userservice.dto.request.UpdateUserRequest;
import com.novapay.userservice.dto.request.UserRequest;
import com.novapay.userservice.dto.response.UserResponse;
import com.novapay.userservice.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "kycVerified", target = "kycVerified")
    @Mapping(source = "kycSubmittedAt", target = "kycSubmittedAt")
    UserResponse toResponseDTO(User user);

    User toUserEntity(UserRequest userRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateUserRequest dto, @MappingTarget User entity);

    void updateKycFromDto(KycRequest dto, @MappingTarget User entity);
}
