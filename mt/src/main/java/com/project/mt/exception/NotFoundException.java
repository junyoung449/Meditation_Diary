package com.project.mt.exception;

public class NotFoundException extends RuntimeException {

	public static final String MEMBER_NOT_FOUND = "존재하지 않는 회원입니다.";
	public static final String MEDITATION_NOT_FOUND = "존재하지 않는 명상 글입니다.";
	public static final String MEMO_NOT_FOUND = "존재하지 않는 메모 글입니다.";

	public NotFoundException(String message) {
		super(message);
	}
}