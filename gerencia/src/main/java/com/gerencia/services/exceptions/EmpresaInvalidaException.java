package com.gerencia.services.exceptions;

public class EmpresaInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmpresaInvalidaException(String msg) {
		super(msg);
	}

}
