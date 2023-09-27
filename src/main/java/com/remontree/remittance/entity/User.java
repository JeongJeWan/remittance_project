package com.remontree.remittance.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    회원에 대한 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    // 사용자 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    // 사용자 상태(가입 or 탈퇴)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_code", nullable = false)
    private UserStatus status;

    // 사용자 이름
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    // 사용자 현재 잔액
    @Column(name = "balance", nullable = false)
    private Integer balance;

    // 사용자 최대 한도
    @Column(name = "max_limit", nullable = false)
    private Integer maxLimit;

    /**
     * 사용자 생성에 대한 생성자
     *
     * @param userStatus    사용자 상태
     * @param username      사용자 이름
     * @param balance       사용자 현재 잔액
     * @param maxLimit      사용자 최대 한도
     */
    public User(UserStatus userStatus, String username, Integer balance, Integer maxLimit) {
        this.status = userStatus;
        this.username = username;
        this.balance = balance;
        this.maxLimit = maxLimit;
    }

    public void updateBalance(Integer balance) {
        this.balance = balance;
    }
}
