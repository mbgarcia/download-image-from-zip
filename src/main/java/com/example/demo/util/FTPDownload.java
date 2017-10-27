package com.example.demo.util;

import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.request.DownloadRequestParams;

@Component
public class FTPDownload {
	
	private static Logger logger = LoggerFactory.getLogger(FTPDownload.class);
	
	public boolean download(DownloadRequestParams params, String zipFolder) {
		try {
			FTPClient ftp = new FTPClient();

			ftp.connect(params.getFtpServer());

			ftp.login(params.getFtpUser(), params.getFtpPass());

			ftp.changeWorkingDirectory(params.getFilePath());

			FileOutputStream output = new FileOutputStream(zipFolder + params.getZipFile());
			
			if (ftp.retrieveFile(params.getZipFile(), output)) {
				logger.info("Download realizado com sucesso.");
				output.close();
				return true;
			} else {
				logger.error("Download falhou!");
				return false;
			}
		} catch (Exception e) {
			logger.error("Erro ao realizar o download!", e);			
			return false;
		}
	}
}
