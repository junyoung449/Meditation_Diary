package com.project.mt.meditation.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meditation_image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MeditationImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_idx")
	private Long imageIdx;

	@ManyToOne
	@JoinColumn(name = "meditation_idx")
	private Meditation meditation;

	@Column(name = "image_url")
	private String imageUrl;
}