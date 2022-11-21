package org.com.service;

import org.com.model.TodoEntity;
import org.com.model.TodoRequest;
import org.com.repository.TodoRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;


    //등록 테스트
    @Test
    void add() {
        when(this.todoRepository.save(any(TodoEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest test01 = new TodoRequest();
        test01.setTitle("Test Title");

        TodoEntity test02 = this.todoService.add(test01);
        assertEquals(test01.getTitle(), test02.getTitle());
    }

    //id로 검색 테스트
    @Test
    void searchById() {
        TodoEntity test = new TodoEntity();
        test.setId(1004L);
        test.setTitle("타이틀입니다요");
        test.setOrder(0L);
        test.setCompleted(false);

        //맵핑~
        Optional<TodoEntity> testOptional = Optional.of(test);

        given(this.todoRepository.findById(anyLong()))
                .willReturn(testOptional);

        TodoEntity test03 = testOptional.get();
        TodoEntity test04 = this.todoService.searchById(1004L);

        assertEquals(test03.getId(), test04.getId());
        assertEquals(test03.getTitle(), test04.getTitle());
        assertEquals(test03.getOrder(), test04.getOrder());
        assertEquals(test03.getCompleted(), test04.getCompleted());
    }

    //id를 찾지 못했을 때의 에러 발생 테스트
    @Test
    public void searchByIdFailed() {
        Optional<Object> testOptional;
        given(this.todoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            this.todoService.searchById(1004L);
        });

    }


}