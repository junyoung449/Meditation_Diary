package com.project.mt.meditation.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@JsonIgnore
	@OneToMany(mappedBy = "meditation_image", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MeditationImage> meditationImage;

	@JsonIgnore
	@OneToMany(mappedBy = "meditation_audio", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MeditationAudio> meditationAudio;
}