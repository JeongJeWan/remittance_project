package com.remontree.remittance.service;

import com.remontree.remittance.dto.request.CreateUserRequestDto;
import com.remontree.remittance.dto.request.RemittanceAmountRequestDto;
import com.remontree.remittance.dto.response.RemittanceResponseDto;
import com.remontree.remittance.dto.response.SelectRemittanceResponseDto;
import com.remontree.remittance.entity.Remittance;
import com.remontree.remittance.entity.User;
import com.remontree.remittance.entity.UserStatus;
import com.remontree.remittance.exception.InsufficientBalanceException;
import com.remontree.remittance.exception.LimitExceededException;
import com.remontree.remittance.repository.remittance.RemittanceRepository;
import com.remontree.remittance.repository.user.UserRepository;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class RemittanceServiceTest {

    @InjectMocks
    private RemittanceService remittanceService;
    @Mock
    private RemittanceRepository remittanceRepository;
    @Mock
    private UserRepository userRepository;

    CreateUserRequestDto testCreateUserRequestDto1;
    CreateUserRequestDto testCreateUserRequestDto2;
    CreateUserRequestDto testCreateUserRequestDto3;
    UserStatus testUserStatus;
    User testSenderUser;
    User testReceiverUser1;
    User testReceiverUser2;

    Integer balance = 500000;

    @BeforeEach
    void setup() {
        testCreateUserRequestDto1 = ReflectionUtils.newInstance(CreateUserRequestDto.class);
        ReflectionTestUtils.setField(testCreateUserRequestDto1, "username", "정제완");
        ReflectionTestUtils.setField(testCreateUserRequestDto1, "maxLimit", 2000000);
        testCreateUserRequestDto2 = ReflectionUtils.newInstance(CreateUserRequestDto.class);
        ReflectionTestUtils.setField(testCreateUserRequestDto2, "username", "홍길동");
        ReflectionTestUtils.setField(testCreateUserRequestDto2, "maxLimit", 2000000);
        testCreateUserRequestDto3 = ReflectionUtils.newInstance(CreateUserRequestDto.class);
        ReflectionTestUtils.setField(testCreateUserRequestDto3, "username", "김철수");
        ReflectionTestUtils.setField(testCreateUserRequestDto3, "maxLimit", 900000);

        testUserStatus = new UserStatus("ACTIVE", "활성");

        testSenderUser = new User(testUserStatus, testCreateUserRequestDto1.getUsername(),
                balance, testCreateUserRequestDto1.getMaxLimit());
        ReflectionTestUtils.setField(testSenderUser, "id", 1L);

        testReceiverUser1 = new User(testUserStatus, testCreateUserRequestDto2.getUsername(),
                balance, testCreateUserRequestDto2.getMaxLimit());
        ReflectionTestUtils.setField(testReceiverUser1, "id", 2L);

        testReceiverUser2 = new User(testUserStatus, testCreateUserRequestDto3.getUsername(),
                balance, testCreateUserRequestDto3.getMaxLimit());
        ReflectionTestUtils.setField(testReceiverUser2, "id", 3L);

    }

    @Test
    @DisplayName("사용자가 다른 사용자게 송금 성공")
    void createRemittance() {
        RemittanceAmountRequestDto remittanceAmountRequestDto = ReflectionUtils.newInstance(RemittanceAmountRequestDto.class);
        ReflectionTestUtils.setField(remittanceAmountRequestDto, "amount", 50000);

        when(userRepository.findById(testSenderUser.getId())).thenReturn(Optional.of(testSenderUser));
        when(userRepository.findById(testReceiverUser1.getId())).thenReturn(Optional.of(testReceiverUser1));

        assertDoesNotThrow(() ->
                remittanceService.createRemittance(testSenderUser.getId(), testReceiverUser1.getId(), remittanceAmountRequestDto));

        verify(userRepository, times(1)).findById(testSenderUser.getId());
        verify(userRepository, times(1)).findById(testReceiverUser1.getId());
        verify(remittanceRepository, times(1)).save(any(Remittance.class));
    }

    @Test
    @DisplayName("사용자가 다른 사용자게 송금 실패 - 잔액 부족")
    void createRemittance_InsufficientBalance() {
        RemittanceAmountRequestDto remittanceAmountRequestDto = ReflectionUtils.newInstance(RemittanceAmountRequestDto.class);
        ReflectionTestUtils.setField(remittanceAmountRequestDto, "amount", 500001);

        when(userRepository.findById(testSenderUser.getId())).thenReturn(Optional.of(testSenderUser));
        when(userRepository.findById(testReceiverUser1.getId())).thenReturn(Optional.of(testReceiverUser1));

        assertThrows(InsufficientBalanceException.class, () ->
                remittanceService.createRemittance(testSenderUser.getId(), testReceiverUser1.getId(), remittanceAmountRequestDto));
    }

    @Test
    @DisplayName("사용자가 다른 사용자게 송금 실패 - 받는 사용자 한도 초과")
    void createRemittance_LimitExceeded() {
        RemittanceAmountRequestDto remittanceAmountRequestDto = ReflectionUtils.newInstance(RemittanceAmountRequestDto.class);
        ReflectionTestUtils.setField(remittanceAmountRequestDto, "amount", 500000);

        when(userRepository.findById(testSenderUser.getId())).thenReturn(Optional.of(testSenderUser));
        when(userRepository.findById(testReceiverUser2.getId())).thenReturn(Optional.of(testReceiverUser2));

        assertThrows(LimitExceededException.class, () ->
                remittanceService.createRemittance(testSenderUser.getId(), testReceiverUser2.getId(), remittanceAmountRequestDto));
    }

    @Test
    @DisplayName("사용자에 대한 송금 목록 조회 성공")
    void selectUserRemittanceList() {
        Long senderUserId = testSenderUser.getId();
        Long receiverUserId = testReceiverUser1.getId(); // 수정: testReceiverUser1의 아이디를 사용

        List<SelectRemittanceResponseDto> remittanceList = Collections.singletonList(
                new SelectRemittanceResponseDto(testSenderUser.getId(), receiverUserId, 50000, LocalDateTime.now())
        );

        when(remittanceRepository.findUserRemittanceList(senderUserId)).thenReturn(remittanceList);
        when(userRepository.findUsernameById(senderUserId)).thenReturn(testSenderUser.getUsername());
        when(userRepository.findUsernameById(receiverUserId)).thenReturn(testReceiverUser1.getUsername()); // 수정: receiverUserId 사용

        List<RemittanceResponseDto> responseList = remittanceService.selectUserRemittanceList(senderUserId);

        assertEquals(1, responseList.size());
        RemittanceResponseDto responseDto = responseList.get(0);
        assertEquals(testSenderUser.getUsername(), responseDto.getSenderName());
        assertEquals(testReceiverUser1.getUsername(), responseDto.getReceiverName());
        assertEquals(50000, responseDto.getAmount());
    }

}