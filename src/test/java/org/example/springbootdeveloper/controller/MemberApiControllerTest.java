package org.example.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootdeveloper.domain.Member;
import org.example.springbootdeveloper.dto.AddMemberRequest;
import org.example.springbootdeveloper.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;    //직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        memberRepository.deleteAll();
    }

    @DisplayName("addMember : 회원 추가에 성공한다.")
    @Test
    public void addMember()throws Exception{
        //given : 회원 추가에 필요한 요청 객체를 만든다.
        final String url = "/api/members";
        final String name = "홍길동";
        final int age = 20;

        final AddMemberRequest memberRequest = new AddMemberRequest(name, age);
        //객체를 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(memberRequest);

        //when : 회원 추가 api에 요청을 보낸다.
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then : 응답코드가 201인지 확인, member 전체 조회해 크기가 1인지 확인하고 실제로 저장한 데이터와 요청 값을 비교
        result.andExpect(status().isCreated());

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getName()).isEqualTo(name);
        assertThat(members.get(0).getAge()).isEqualTo(age);
    }

    @DisplayName("findAllMembers : 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAlMembers() throws Exception {
        //given : 회원을 저장한다.
        final String url = "/api/members";
        final String name = "홍길동";
        final int age = 20;

        memberRepository.save(Member.builder()
                .name(name)
                .age(age)
                .build());
        //when : 목록 조회 api를 호출한다.
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then : 응답코드가 200이고, 반환받은 값 중 0번째 요서의 content, title 값이 같은지 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));
    }
}