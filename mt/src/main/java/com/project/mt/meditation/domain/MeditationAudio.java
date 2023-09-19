package com.project.mt.meditation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meditation_audio")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MeditationAudio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "audio_idx")
	private Long audioIdx;

	@ManyToOne
	@JoinColumn(name = "meditation_idx")
	private Meditation meditation;

	@Column(name = "audio_url")
	private String audioUrl;
}