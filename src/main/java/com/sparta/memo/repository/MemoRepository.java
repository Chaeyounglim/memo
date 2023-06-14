package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();
    // ModifiedAt 필드 데이터 기준으로 내림차순 하여 전체 데이터를 가져온다.
    // 정해진 규칙에 맞게 메서드를 선언하면 SimpleJpaRepository 구현체가 생성될 때 자동으로 만들어진다.

    // 내용에 특정 키워드가 포함된 메모를 조회하는데, 수정시간 기준 내림차순
    //List<Memo> findAllByContentsLikeOrderByModifiedAtDesc(String keyword);
    List<Memo> findAllByContentsContainsOrderByModifiedAtDesc(String keyword);
    // 내용에 특정 키워드가 포함된 메모를 조회하는데, 수정시간 기준 내림차순

/*
    List<Memo> findAllByUsername(String username);
    // 해당 메서드를 호출할 때 파라미터에 username을 넣어주면 where 조건문에 전달되어 메서드 실행
*/

}
