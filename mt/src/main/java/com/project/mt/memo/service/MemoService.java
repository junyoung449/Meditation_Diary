package com.project.mt.memo.service;

import com.project.mt.exception.NotFoundException;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import com.project.mt.memo.domain.Memo;
import com.project.mt.memo.dto.request.MemoRequestDto;
import com.project.mt.memo.dto.response.MemoResponseDto;
import com.project.mt.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;

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

     public MemoResponseDto findMemoByMemoIdx(Long memoIdx) {
         Memo memo = memoRepository.findMemoById(memoIdx).orElseThrow(() -> new NotFoundException(NotFoundException.MEMO_NOT_FOUND));
         return new MemoResponseDto(memo.getId(), memo.getMember().getMemberIdx(), memo.getContent(), memo.getDate());
     }

     public MemoResponseDto saveMemo(MemoRequestDto memoRequestDto) {
         Member member = memberRepository.findMemberByMemberIdx(memoRequestDto.getMemberIdx()).orElseThrow(()
                 -> new NotFoundException(NotFoundException.MEMBER_NOT_FOUND));

         Memo memo = memoRepository.save(Memo.builder()
                 .content(memoRequestDto.getContent())
                 .member(member)
                 .build());

         return new MemoResponseDto(memo.getId(), memo.getMember().getMemberIdx(), memo.getContent(), memo.getDate());
     }

     public MemoResponseDto modifyMemo(MemoRequestDto memoRequestDto) {
         Member member = memberRepository.findMemberByMemberIdx(memoRequestDto.getMemberIdx()).orElseThrow(()
                 -> new NotFoundException(NotFoundException.MEMBER_NOT_FOUND));

         try {
             Memo memo = memoRepository.save(Memo.builder()
                     .id(memoRequestDto.getMemoIdx())
                     .content(memoRequestDto.getContent())
                     .date(LocalDateTime.now())
                     .member(member)
                     .build());

             return new MemoResponseDto(memo.getId(), memo.getMember().getMemberIdx(), memo.getContent(), memo.getDate());
         } catch (Exception e) {
             throw new NotFoundException(NotFoundException.MEMO_NOT_FOUND);
         }
     }

    public boolean deleteMemo(Long memoIdx) {
        try {
            memoRepository.deleteById(memoIdx);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
