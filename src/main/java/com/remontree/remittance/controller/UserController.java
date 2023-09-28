package com.remontree.remittance.controller;

import com.remontree.remittance.dto.request.ChargeBalanceRequestDto;
import com.remontree.remittance.dto.request.CreateUserRequestDto;
import com.remontree.remittance.dto.request.RemittanceAmountRequestDto;
import com.remontree.remittance.dto.response.RemittanceResponseDto;
import com.remontree.remittance.service.RemittanceService;
import com.remontree.remittance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자에 대한 생성 및 송금, 금액 충전 Controller.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RemittanceService remittanceService;

    /**
     * 사용자 생성에 대한 메서드.
     *
     * @param requestDto    생성 시 요청되는 Dto
     * @return              상태코드 201(CREATED)와 함께 응답을 반환
     */
    @PostMapping
    public ResponseEntity<Void> postCreateUser(CreateUserRequestDto requestDto) {
        userService.createUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 사용자에 대한 금액 충전 메서드.
     *
     * @param userId        사용자 아이디
     * @param requestDto    충전 금액 시 요청되는 Dto
     * @return              상태코드 200(Ok)와 함께 응답을 반환
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> patchChargeBalance(@PathVariable Long userId,
                                                   ChargeBalanceRequestDto requestDto) {
        userService.chargeBalance(userId, requestDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 사용자가 다른 사용자게 송금하는 메서드.
     *
     * @param senderId      보내는 사용자
     * @param receiverId    받는 사용자
     * @param requestDto    송금 시 요청되는 Dto
     * @return              상태코드 201(CREATED)와 함께 응답을 반환
     */
    @PostMapping("/remittance/{senderId}/{receiverId}")
    public ResponseEntity<Void> postCreateRemittance(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId,
                                                     RemittanceAmountRequestDto requestDto) {

        remittanceService.createRemittance(senderId, receiverId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 사용자에 대한 송금 목록 조회 메서드.
     *
     * @param userId    사용자 아이디
     * @return          상태코드 200(Ok)와 함께 응답을 반환 & 사용자 송금 목록 조회 반환
     */
    @GetMapping("/remittance/{userId}")
    public ResponseEntity<List<RemittanceResponseDto>> getUserRemittanceList(@PathVariable Long userId) {

        List<RemittanceResponseDto> responseDtoList = remittanceService.selectUserRemittanceList(userId);

        return ResponseEntity.ok(responseDtoList);
    }
}
