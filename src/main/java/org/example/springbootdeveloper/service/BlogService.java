package org.example.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.domain.Article;
import org.example.springbootdeveloper.dto.AddArticleRequest;
import org.example.springbootdeveloper.dto.UpdateArticleRequest;
import org.example.springbootdeveloper.event.BlogCreatedEvent;
import org.example.springbootdeveloper.repository.BlogRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor  //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Transactional
public class BlogService {
    private final BlogRepository blogRepository;
    //이벤트를 발생시키기 위해 빈을 주입
    private final ApplicationEventPublisher eventPublisher;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        //블로그 글이 추가 되는 시점에 이벤트 발생
        executeEventTrigger(request);
        return blogRepository.save(request.toEntity());
    }

    public void executeEventTrigger(AddArticleRequest request){
        eventPublisher.publishEvent(new BlogCreatedEvent(request.toEntity()));
    }

    //블로그 글 목록 조회
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    //블로그 글 상세 조회
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    //블로그 글 삭제
    public void deleteById(long id){
        blogRepository.deleteById(id);
    }

    //블로그 글 수정
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
