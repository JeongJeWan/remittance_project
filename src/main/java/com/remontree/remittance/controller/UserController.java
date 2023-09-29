package com.remontree.remittance.controller;

import com.remontree.remittance.dto.request.ChargeBalanceRequestDto;
import com.remontree.remittance.dto.request.CreateUserRequestDto;
import com.remontree.remittance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 사용자에 대한 생성 및 금액 충전 Controller.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 생성에 대한 메서드.
     *
     * @param requestDto    생성 시 요청되는 Dto
     * @return              상태코드 201(CREATED)와 함께 응답을 반환
     */
    @PostMapping
    public ResponseEntity<Void> postCreateUser(@RequestBody CreateUserRequestDto requestDto) {
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
                                                   @RequestBody ChargeBalanceRequestDto requestDto) {
        userService.chargeBalance(userId, requestDto);

        return ResponseEntity.ok().build();
    }
}
