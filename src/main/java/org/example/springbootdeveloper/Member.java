package org.example.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/*
    자동키 생성 설정 방식
    @GeneratedValue(strategy = GenerationType.???)
    AUTO :  선택한 데이터베이스 방언에 따라 방식을 자동으로 선택(기본값)
    IDENTITY : AUTO_INCREMENT
    SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본키를 할당(오라클)
    TABLE : 키 생성 테이블 사용
 */
/*
    @Column 애너테이션의 속성
    name : 필드와 매핑할 컬럼 이름. 설정하지 않으면 필드 이름으로 지정
    nullable : null 여부(true, false)
    unique : 컬럼의 유일한 값 여부. 설정 안하면 false
    columnDefinition : 컬럼의 정보 설정. default 값
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //기본 생성자: 엔티티에는 반드시 있어야 함. public 보다 protected가 안전
@AllArgsConstructor
@Getter
@Entity //엔티티로 지정
//@Entity(name = "member") -> name을 가진 테이블 이름과 매핑
public class Member {
    @Id //id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;
    
    @Column(name = "name", nullable = false)    //name이라는 not null 컬럼과 매핑
    private String name;
}
