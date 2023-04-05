package com.gerencia.services.exceptions;

public class FornecedorNaoEncontradException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public FornecedorNaoEncontradException(String msg) {
		super(msg);
	}

}
