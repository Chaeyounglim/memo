package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long,Memo> memoList =new HashMap<>();
    // Long 타입의 id값을 넣을 예정.
    // DB 역할을 한다고 생각.

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto RequestDto){
        // RequestDto -> Entity
        Memo memo = new Memo(RequestDto);

        // Memo Max Id check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        // memoList의 모든 key 값 중에서 가장 큰 값 + 1 을 반환. 0일 경우는 1로
        memo.setId(maxId);

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map to List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();
        // memoList에 values를 각각 MemoResponseDto 타입 객체로 변환하여 List로 만들어줌.
        // MemoResponseDto::new 를 하면 MemoResponseDto 객체에 생성자를 호출

        return responseList;

    }
}
