package com.remontree.remittance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remontree.remittance.dto.request.RemittanceAmountRequestDto;
import com.remontree.remittance.dto.response.RemittanceResponseDto;
import com.remontree.remittance.service.RemittanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RemittanceController.class)
class RemittanceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @MockBean
    private RemittanceService remittanceService;
    Long senderId = 1L;
    Long receiverId = 2L;
    @Test
    @DisplayName("사용자가 다른 사용자에게 송금 성공")
    void postCreateRemittance() throws Exception{
        RemittanceAmountRequestDto remittanceAmountRequestDto = ReflectionUtils.newInstance(RemittanceAmountRequestDto.class);
        ReflectionTestUtils.setField(remittanceAmountRequestDto, "amount", 50000);

        mockMvc.perform(post("/api/user/remittance/{senderId}/{receiverId}", senderId, receiverId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(remittanceAmountRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("사용자에 대한 송금 목록 조회 성공")
    void getUserRemittanceList() throws Exception{
        List<RemittanceResponseDto> responseDtoList = new ArrayList<>();

        RemittanceResponseDto remittanceResponseDto =
                new RemittanceResponseDto("정제완", "홍길동", 50000, LocalDateTime.now());
        responseDtoList.add(remittanceResponseDto);

        when(remittanceService.selectUserRemittanceList(senderId)).thenReturn(responseDtoList);

        mockMvc.perform(get("/api/user/remittance/{userId}", senderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].senderName").value(remittanceResponseDto.getSenderName()))
                .andExpect(jsonPath("$[0].receiverName").value(remittanceResponseDto.getReceiverName()))
                .andExpect(jsonPath("$[0].amount").value(remittanceResponseDto.getAmount()))
                .andExpect(jsonPath("$[0].createdAt").exists());
    }
}