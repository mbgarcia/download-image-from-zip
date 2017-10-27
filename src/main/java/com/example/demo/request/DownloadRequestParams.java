package com.example.demo.request;

import io.swagger.annotations.ApiModelProperty;

public class DownloadRequestParams {

	@ApiModelProperty(notes="Servidor FTP", example = "192.168.0.1")
	private String ftpServer;
	
	@ApiModelProperty(notes="Usuario do FTP", example = "ftpuser")
	private String ftpUser;
	
	@ApiModelProperty(notes="Senha do Usuario do FTP", example = "ftppass")
	private String ftpPass;
	
	@ApiModelProperty(notes="Pasta dos arquivos", example = "/FOLDER/SUBFOLDER/")
	private String filePath;
	
	@ApiModelProperty(notes="Arquivo compactado para download", example = "fotos.zip")
	private String zipFile;
	
	@ApiModelProperty(notes="Imagem do arquivo compactado para download", example = "alvo.jpeg")
	private String fileEntry;

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPass() {
		return ftpPass;
	}

	public void setFtpPass(String ftpPass) {
		this.ftpPass = ftpPass;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getZipFile() {
		return zipFile;
	}

	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}

	public String getFileEntry() {
		return fileEntry;
	}

	public void setFileEntry(String fileEntry) {
		this.fileEntry = fileEntry;
	}

	public String toString() {
		return "{ftpServer:" + ftpServer + ", filePath:" + filePath + ", zipFile:" + zipFile + ", fileEntry:"
				+ fileEntry + "}";
	}
}
