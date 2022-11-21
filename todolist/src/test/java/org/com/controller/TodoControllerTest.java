package org.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.model.TodoEntity;
import org.com.model.TodoRequest;
import org.com.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;
    private TodoEntity expected;

    //테스트 실행 전에 늘 실행 -> 초기화!
    @BeforeEach
    void setup() {
        this.expected = new TodoEntity();
        this.expected.setId(1004L);
        this.expected.setTitle("타이틀입니둥두웅");
        this.expected.setOrder(0L);
        this.expected.setCompleted(false);
    }


    //create
    @Test
    void create() throws Exception{
        when(this.todoService.add(any(TodoRequest.class)))
                .then((i) -> {
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new TodoEntity(this.expected.getId(), request.getTitle(), this.expected.getOrder(), this.expected.getCompleted());
                });

        TodoRequest request = new TodoRequest();
        request.setTitle("하나의 타이틀 예아");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("하나의 타이틀 예아"));
    }

    @Test
    void readOne() {
    }
}