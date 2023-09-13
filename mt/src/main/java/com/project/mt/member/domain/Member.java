package com.project.mt.member.domain;

import javax.persistence.*;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_idx")
	private Long memberIdx;

	@Column(name = "email")
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "social_id")
	private String socialId;

}
