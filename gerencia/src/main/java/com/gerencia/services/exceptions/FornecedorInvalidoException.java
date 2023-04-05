package com.gerencia.services.exceptions;

public class FornecedorInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FornecedorInvalidoException(String msg) {
		super(msg);
	}

}
