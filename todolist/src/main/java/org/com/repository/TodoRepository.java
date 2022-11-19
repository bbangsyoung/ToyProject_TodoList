package org.com.repository;

import org.com.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//제네릭타입 앞에는 데이터베이스를 테이블과 연결될 객체인 TodoEntity, 뒤에는 Id에 해당하는 Long 타입을 기재
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
