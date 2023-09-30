package com.project.mt.voice.domain;

import com.project.mt.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Builder
public class Voice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voice_idx")
    private Long voiceIdx;

    @Column(name = "voice_name")
    private String voiceName;

    @Column(name = "model_id")
    private String modelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;
}
