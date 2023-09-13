package com.project.mt.meditation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
