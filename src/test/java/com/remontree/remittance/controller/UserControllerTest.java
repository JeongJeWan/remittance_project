package com.remontree.remittance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remontree.remittance.dto.request.ChargeBalanceRequestDto;
import com.remontree.remittance.dto.request.CreateUserRequestDto;
import com.remontree.remittance.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @MockBean
    private UserService userService;
    Long userId = 1L;

    @Test
    @DisplayName("사용자 생성 성공")
    void postCreateUser() throws Exception {
        CreateUserRequestDto createUserRequestDto = ReflectionUtils.newInstance(CreateUserRequestDto.class);
        ReflectionTestUtils.setField(createUserRequestDto, "username", "정제완");
        ReflectionTestUtils.setField(createUserRequestDto, "maxLimit", 2000000);

        mockMvc.perform(post("/api/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(createUserRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("사용자에 대한 금액 충전 성공")
    void patchChargeBalance() throws Exception{
        ChargeBalanceRequestDto chargeBalanceRequestDto = ReflectionUtils.newInstance(ChargeBalanceRequestDto.class);
        ReflectionTestUtils.setField(chargeBalanceRequestDto, "balance", 50000);

        mockMvc.perform(patch("/api/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(chargeBalanceRequestDto)))
                .andExpect(status().isOk());
    }
}