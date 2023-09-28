package com.remontree.remittance.service;

import com.remontree.remittance.dto.request.RemittanceAmountRequestDto;
import com.remontree.remittance.dto.response.RemittanceResponseDto;
import com.remontree.remittance.dto.response.SelectRemittanceResponseDto;
import com.remontree.remittance.entity.Remittance;
import com.remontree.remittance.entity.User;
import com.remontree.remittance.exception.InsufficientBalanceException;
import com.remontree.remittance.exception.LimitExceededException;
import com.remontree.remittance.exception.UserNotFoundException;
import com.remontree.remittance.repository.remittance.RemittanceRepository;
import com.remontree.remittance.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemittanceService {

    private final UserRepository userRepository;
    private final RemittanceRepository remittanceRepository;

    /**
     * 사용자가 다른 사용자에게 송금하는 메서드. <br>
     * 사용자 잔액이 송금하는 금액보다 적을 경우 예외 처리 <br>
     * 수신자 잔액이 한도 초과할 경우 예외 처리 <br>
     * 검증 통과 후, 사용자 잔액에서 송금 금액 마이너스 후 송금 정보 저장
     *
     * @param senderId      보내는 사용자 아이디
     * @param receiverId    받는 사용자 아이디
     * @param requestDto    보내는 사용자 송금 금액 dto
     */
    public void createRemittance(Long senderId, Long receiverId, RemittanceAmountRequestDto requestDto) {

        User sender = userRepository.findById(senderId).orElseThrow(UserNotFoundException::new);
        User receiver = userRepository.findById(receiverId).orElseThrow(UserNotFoundException::new);

        verificationRemittance(requestDto.getAmount(), sender, receiver);

        Remittance remittance = new Remittance(sender, receiver, requestDto.getAmount());

        sender.updateBalance(sender.getBalance() - requestDto.getAmount());
        receiver.updateBalance(receiver.getBalance() + requestDto.getAmount());
        remittanceRepository.save(remittance);
    }

    /**
     * 사용자에 대한 송금 목록 조회 메서드. <br>
     * 사용자에 대한 송금 목록 조회 후, 반환할 때 Id 가 아닌 보내는 사람, 받는 사람에 대한 <br>
     * username 으로 변환 후 반환
     *
     * @param userId    사용자 아이디
     * @return          사용자에 대한 송금 목록 반환
     */
    public List<RemittanceResponseDto> selectUserRemittanceList(Long userId) {

        // 사용자에 대한 송금 목록 조회
        List<SelectRemittanceResponseDto> remittanceList = remittanceRepository.findUserRemittanceList(userId);

        // 송신자(sender)와 수신자(receiver)의 사용자 이름(username) 변환 후 반환
        List<RemittanceResponseDto> responseList = remittanceList.stream()
                .map(remittance -> {
                    String senderName = userRepository.findUsernameById(remittance.getSenderId());
                    String receiverName = userRepository.findUsernameById(remittance.getReceiverId());

                    return new RemittanceResponseDto(senderName, receiverName, remittance.getAmount(), remittance.getCreatedAt());
                })
                .collect(Collectors.toList());

        return responseList;
    }

    private static void verificationRemittance(Integer amount, User sender, User receiver) {

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }
        if (receiver.getBalance() + amount > receiver.getMaxLimit()) {
            throw new LimitExceededException("수신자의 한도를 초과했습니다.");
        }
    }
}
