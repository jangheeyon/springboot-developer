package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.domain.Member;
import org.example.springbootdeveloper.dto.AddMemberRequest;
import org.example.springbootdeveloper.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //블로그 글 추가 메서드
    public Member save(AddMemberRequest request){
        return memberRepository.save(request.toEntity());
    }

    //블로그 글 목록 조회
    public List<Member> findAll(){
        return memberRepository.findAll();
    }
}
