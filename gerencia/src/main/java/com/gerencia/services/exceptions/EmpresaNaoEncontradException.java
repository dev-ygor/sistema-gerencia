package com.gerencia.services.exceptions;


public class EmpresaNaoEncontradException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmpresaNaoEncontradException(String msg) {
		super(msg);
	}

}
