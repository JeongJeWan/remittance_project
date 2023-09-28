package com.remontree.remittance.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users_status")
public class UserStatus {

    // 사용자 상태 식별 코드
    @Id
    @Column(name = "status_code", nullable = false, length = 10)
    private String statusCode;

    // 사용자 상태 내용
    @Column(name = "name", nullable = false, length = 10)
    private String name;

    public UserStatus(String statusCode, String name) {
        this.statusCode = statusCode;
        this.name = name;
    }

    /**
     * 사용자 상태코드들을 상수로 관리하기 위한 Enum.
     */
    public enum Code {
        ACTIVE, WITHDRAWAL;

        public static boolean matches(String statusCode) {
            return Arrays.stream(UserStatus.Code.values())
                    .anyMatch(a -> a.name().equals(statusCode));
        }
    }
}
