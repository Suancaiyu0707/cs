package com.casaba.common.base;

public class CasadaException extends RuntimeException {
	private static final long serialVersionUID = -1219262335729891920L;

	public CasadaException(final String message) {
		super(message);
	}

	public CasadaException(final Throwable cause) {
		super(cause);
	}

	public CasadaException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
