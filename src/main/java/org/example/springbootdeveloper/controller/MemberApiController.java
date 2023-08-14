package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.domain.Member;
import org.example.springbootdeveloper.dto.AddMemberRequest;
import org.example.springbootdeveloper.dto.MemberResponse;
import org.example.springbootdeveloper.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<Member> addMember(@RequestBody AddMemberRequest request){
        Member savedMember = memberService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedMember);
    }

    @GetMapping("api/members")
    public ResponseEntity<List<MemberResponse>> findAllMembers(){
        List<MemberResponse> members = memberService.findAll()
                .stream()
                .map(MemberResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(members);
    }
}
