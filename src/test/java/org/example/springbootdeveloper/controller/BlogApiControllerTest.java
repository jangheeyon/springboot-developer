package org.example.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootdeveloper.domain.Article;
import org.example.springbootdeveloper.dto.AddArticleRequest;
import org.example.springbootdeveloper.repository.BlogRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc   //MockMvc 생성 및 자동 구성
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;    //직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle()throws Exception{
        //given : 블로그 글 추가에 필요한 요청 객체를 만든다.
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        //객체를 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when : 블로그 글 추가 api에 요청을 보낸다.
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then : 응답코드가 201인지 확인, blog 전체 조회해 크기가 1인지 확인하고 실제로 저장한 데이터와 요청 값을 비교
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles : 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        //given : 블로그 글을 저정한다.
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());
        //when : 목록 조회 api를 호출한다.
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then : 응답코드가 200이고, 반환받은 값 중 0번째 요서의 content, title 값이 같은지 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("findArticle : 블로그 글 상세 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        //given : 블로그 글을 저장한다.
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());
        //when : 저장한 블로그 글의 id 값으로 api를 호출한다.
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        //then : 응답코드가 200이고, 반환받은 content와 title이 저장한 값과 같은지 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName(("deleteArticle : 블로그 글 삭제에 성공한다."))
    @Test
    public void deleteArticle() throws Exception {
        //given : 블로그 글을 저정한다.
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when : 저장한 글의 id 값으로 삭제 api를 호출한다.
        mockMvc.perform(delete(url, savedArticle.getId())).andExpect(status().isOk());

        //then : 응답코드가 200, 블로그 글 리스트 전체를 조회해 조회한 배열 크기가 0인지 확인
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }
}