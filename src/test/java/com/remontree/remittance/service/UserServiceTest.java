package com.remontree.remittance.service;

import com.remontree.remittance.dto.request.ChargeBalanceRequestDto;
import com.remontree.remittance.dto.request.CreateUserRequestDto;
import com.remontree.remittance.entity.User;
import com.remontree.remittance.entity.UserStatus;
import com.remontree.remittance.exception.LimitExceededException;
import com.remontree.remittance.exception.UserNotFoundException;
import com.remontree.remittance.repository.user.UserRepository;
import com.remontree.remittance.repository.userstatus.UserStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserStatusRepository userStatusRepository;

    CreateUserRequestDto testCreateUserRequestDto;
    UserStatus testUserStatus;
    User testUser;
    Integer balance = 500000;

    @BeforeEach
    void setup() {
        testCreateUserRequestDto = ReflectionUtils.newInstance(CreateUserRequestDto.class);
        ReflectionTestUtils.setField(testCreateUserRequestDto, "username", "정제완");
        ReflectionTestUtils.setField(testCreateUserRequestDto, "maxLimit", 2000000);

        testUserStatus = new UserStatus("ACTIVE", "활성");

        testUser = new User(testUserStatus, testCreateUserRequestDto.getUsername(),
                balance, testCreateUserRequestDto.getMaxLimit());
    }

    @Test
    @DisplayName("사용자 생성 대한 성공")
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userStatusRepository.getReferenceById(anyString())).thenReturn(testUserStatus);

        assertDoesNotThrow(() -> userService.createUser(testCreateUserRequestDto));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("사용자 금액 충전 성공")
    void chargeBalance() {
        ChargeBalanceRequestDto chargeBalanceRequestDto = ReflectionUtils.newInstance(ChargeBalanceRequestDto.class);
        ReflectionTestUtils.setField(chargeBalanceRequestDto, "balance", 50000);

        // 테스트에서 userRepository.findById 메서드가 호출될 때 testUser 객체를 리턴하도록 설정
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        userService.chargeBalance(testUser.getId(), chargeBalanceRequestDto);

        // 사용자 잔액 충전 후, userRepository.findById 메서드가 호출되었는지 검증
        verify(userRepository, times(1)).findById(testUser.getId());

        // 사용자 잔액이 올바르게 갱신되었는지 검증
        assertEquals(balance + chargeBalanceRequestDto.getBalance(), testUser.getBalance());
    }

    @Test
    @DisplayName("사용자 금액 충전 실패 - 사용자가 존재하지 않을 때")
    void chargeBalance_UserNotFound() {
        ChargeBalanceRequestDto chargeBalanceRequestDto = ReflectionUtils.newInstance(ChargeBalanceRequestDto.class);
        ReflectionTestUtils.setField(chargeBalanceRequestDto, "balance", 50000);

        // 테스트에서 userRepository.findById 메서드가 호출될 때 testUser 객체를 리턴하도록 설정
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.chargeBalance(testUser.getId(), chargeBalanceRequestDto));

        verify(userRepository, times(1)).findById(testUser.getId());
    }

    @Test
    @DisplayName("사용자 금액 충전 실패 - 사용자 한도 초과")
    void createRemittance_LimitExceeded() {
        ChargeBalanceRequestDto chargeBalanceRequestDto = ReflectionUtils.newInstance(ChargeBalanceRequestDto.class);
        ReflectionTestUtils.setField(chargeBalanceRequestDto, "balance", 5000000);

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        assertThrows(LimitExceededException.class, () ->
                userService.chargeBalance(testUser.getId(), chargeBalanceRequestDto));
    }
}