package com.project.mt.member.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import com.project.mt.authentication.domain.oauth.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	@Column(name = "oauth_provider")
	@Enumerated(EnumType.STRING)
	private OAuthProvider oAuthProvider;
}
