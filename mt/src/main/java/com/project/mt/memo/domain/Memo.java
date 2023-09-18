package com.project.mt.memo.domain;

import com.project.mt.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Builder
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_idx")
    private Long id;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

//    @Builder
//    public Memo(String content) {
//        this.content = content;
//    }

}
