package org.example.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {
        Person person = new Person();
        person.setId(1L);
        person.setName("홍길동");
        person.setAge(11);
        person.setHobbies(List.of("수영", "헬스"));

        model.addAttribute("person", person);
        model.addAttribute("today", LocalDate.now());
        return "example";   //example.html 라는 뷰 조회
    }

    @Getter
    @Setter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }

}
