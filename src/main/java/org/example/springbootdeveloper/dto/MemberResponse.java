package org.example.springbootdeveloper.dto;

import lombok.Getter;
import org.example.springbootdeveloper.domain.Member;

@Getter
public class MemberResponse {
    private final String name;
    private final int age;

    public MemberResponse(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
    }
}
