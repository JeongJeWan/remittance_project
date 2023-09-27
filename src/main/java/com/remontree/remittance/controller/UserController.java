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

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RemittanceService remittanceService;

    @PostMapping
    public ResponseEntity<Void> postCreateUser(CreateUserRequestDto requestDto) {
        userService.createUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> patchChargeBalance(@PathVariable Long userId,
                                                   ChargeBalanceRequestDto requestDto) {
        userService.chargeBalance(userId, requestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remittance/{senderId}/{receiverId}")
    public ResponseEntity<Void> postCreateRemittance(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId,
                                                     RemittanceAmountRequestDto requestDto) {

        remittanceService.createRemittance(senderId, receiverId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/remittance/{userId}")
    public ResponseEntity<List<RemittanceResponseDto>> getUserRemittanceList(@PathVariable Long userId) {

        List<RemittanceResponseDto> responseDtoList = remittanceService.selectUserRemittanceList(userId);

        return ResponseEntity.ok(responseDtoList);
    }
}
