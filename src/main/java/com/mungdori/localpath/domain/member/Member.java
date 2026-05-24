package com.mungdori.localpath.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private LocalDateTime registeredAt;
    private LocalDateTime deactivatedAt;

    public static Member register(MemberRegisterRequest createRequest) {
        Member member = new Member();

        member.email = requireNonNull(createRequest.email());
        member.name = requireNonNull(createRequest.name());
        member.registeredAt = LocalDateTime.now();

        return member;

    }
}
