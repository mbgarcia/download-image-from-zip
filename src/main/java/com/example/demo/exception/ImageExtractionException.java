package com.example.demo.exception;

public class ImageExtractionException extends BaseRuntimeException {
	private static final long serialVersionUID = -7242069588698406239L;

	public ImageExtractionException(Object... parametros) {
		super("Erro ao extrair imagem [%s] do arquivo zip ", parametros);
	}

	public ImageExtractionException(Throwable e, Object... parametros) {
		super("Erro ao extrair imagem [%s] do arquivo zip ", e, parametros);
	}
}
