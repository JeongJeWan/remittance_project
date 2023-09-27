package com.remontree.remittance.service;

import com.remontree.remittance.dto.request.ChargeBalanceRequestDto;
import com.remontree.remittance.dto.request.CreateUserRequestDto;
import com.remontree.remittance.entity.User;
import com.remontree.remittance.entity.UserStatus;
import com.remontree.remittance.exception.UserNotFoundException;
import com.remontree.remittance.repository.user.UserRepository;
import com.remontree.remittance.repository.userstatus.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final static Integer DEFAULT_AMOUNT = 0;

    /**
     * 사용자 생성에 대한 메서드.
     *
     * @param requestDto    생성 시 필요한 정보
     * @return              생성 후 사용자 아이디 반환
     */
    public void createUser(CreateUserRequestDto requestDto) {

        UserStatus userStatus = userStatusRepository.getReferenceById(UserStatus.Code.ACTIVE.name());

        User user = new User(
                userStatus, requestDto.getUsername(),
                DEFAULT_AMOUNT, requestDto.getMaxLimit());
        userRepository.save(user);
    }

    /**
     * 사용자 잔액 충전 메서드.
     *
     * @param userId    사용자 아이디
     * @param balance   사용자 잔액
     * @return          잔액 충전 후 현재 잔액 반환
     */
    public void chargeBalance(Long userId, ChargeBalanceRequestDto requestDto) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.updateBalance(user.getBalance() + requestDto.getBalance());
    }
}
