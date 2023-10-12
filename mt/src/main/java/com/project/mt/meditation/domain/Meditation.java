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
import org.hibernate.annotations.CreationTimestamp;

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

	@CreationTimestamp
	@Column(name = "date")
	private Timestamp date;

	@JsonIgnore
	@OneToMany(mappedBy = "meditation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MeditationMedia> meditationMedia;
}