package com.project.mt.memo.service;

import com.project.mt.memo.domain.Memo;
import com.project.mt.memo.dto.response.MemoResponseDto;
import com.project.mt.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemoService {

    private final MemoRepository memoRepository;

    @Transactional
    public List<MemoResponseDto> findMemberMemoList(Long memberIdx) {
        List<Memo> memberMemo = memoRepository.findMemberMemo(memberIdx);
        List<MemoResponseDto> result = memberMemo.stream()
                .map(memo -> MemoResponseDto.builder()
                        .memo_idx(memo.getId())
                        .content(memo.getContent())
                        .date(memo.getDate())
                        .build())
                .collect(Collectors.toList());

        return result;
    }
}
