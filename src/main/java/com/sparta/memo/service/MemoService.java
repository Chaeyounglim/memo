package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemoService {
    // 비즈니스 로직 수정이 필요한 경우엔 Service

    // 생성자를 통한 주입 방법
    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // DB 조회
        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        Memo memo = findMemo(id);

        // memo 내용 수정
        memo.update(requestDto);

        return id;
    }

    public Long deleteMemo(Long id) {
        Memo memo = findMemo(id);

        // memo 삭제
        memoRepository.delete(memo);

        return id;
    }

    private Memo findMemo(Long id){
        // 해당 메모가 DB에 존재하는지 확인
        // null 값이 아니라면 memo가 반환. & null인 경우 throw 던짐.
        return memoRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 메모 미존재")
        );
    }


    public List<MemoResponseDto> getMemosByKeyword(String keyword) {

/*        // Like 사용
        keyword = "%"+ keyword + "%";
        return memoRepository.findAllByContentsLikeOrderByModifiedAtDesc(keyword).stream().map(MemoResponseDto::new).toList();
        */

        // Contains 사용
        return memoRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword).stream().map(MemoResponseDto::new).toList();
    }
}
