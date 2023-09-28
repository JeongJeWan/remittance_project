package com.remontree.remittance.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "remittance")
public class Remittance {

    // 송금 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "remittance_id", nullable = false)
    private Long id;

    // 송금시 보내는 사용자
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // 송금시 받는 사용자
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    // 송금시 보내는 금액
    @Column(name = "amount", nullable = false)
    private Integer amount;

    // 송금되는 시간
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 송금 생성에 대한 생성자
    public Remittance(User sender, User receiver, Integer amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
}
