package org.example.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springbootdeveloper.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddMemberRequest {
    private String name;
    private int age;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .age(age)
                .build();
    }
}
