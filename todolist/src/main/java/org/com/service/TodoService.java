package org.com.service;

import lombok.AllArgsConstructor;
import org.com.model.TodoEntity;
import org.com.model.TodoRequest;
import org.com.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    //할일 추가(requrst를 받아서 entity를 반환)
    public TodoEntity add(TodoRequest request) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setCompleted(request.getCompleted());

        /* TodoEntity entity = this.todoRepository.save(todoEntity); */
        return this.todoRepository.save(todoEntity); //데이터베이스에 저장
    }

    //조회되는 할일을 반환, id기준으로 조회
    public TodoEntity searchById(Long id){
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); //id가 없을 경우 404코드 발생
    }

    //할일 리스트 전체 조회
    public List<TodoEntity> searchAll() {
        return this.todoRepository.findAll();
    }

    //할일 수정
    public TodoEntity updateById(Long id, TodoRequest request) {
        TodoEntity todoEntity = this.searchById(id);
        if (request.getTitle() != null) { //null이 아닐 시
            todoEntity.setTitle(request.getTitle()); //타이틀 값을 변경
        }
        if(request.getOrder() != null) {
            todoEntity.setOrder(request.getOrder());
        }
        if(request.getCompleted() != null) {
            todoEntity.setCompleted(request.getCompleted());
        }

        return this.todoRepository.save(todoEntity); //결과값을 데이터베이스에 저장
    }

    //삭제할 엔티티 하나만 선택해서 삭제
    public void deletedById(Long id) {
        this.todoRepository.deleteById(id);
    }

    //전체삭제
    public void deleteAll() {
        this.todoRepository.deleteAll();
    }




}
