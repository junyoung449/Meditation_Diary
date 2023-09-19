package com.project.mt.memo.repository;

import com.project.mt.memo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Query("SELECT m FROM Memo m WHERE m.memoIdx = :memberIdx")
    List<Memo> findMemberMemo(@Param("memberIdx") Long memberIdx);


    Optional<Memo> findMemoByMemoIdx(Long memoIdx);

}
