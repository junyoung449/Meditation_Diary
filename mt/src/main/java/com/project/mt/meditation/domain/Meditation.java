package com.project.mt.meditation.domain;

import javax.persistence.*;
import java.sql.Timestamp;

import com.project.mt.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meditation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Meditation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meditation_idx")
	private Long meditationIdx;

	@ManyToOne
	@JoinColumn(name = "member_idx")
	private Member member;

	@Column(name = "date")
	private Timestamp date;
}