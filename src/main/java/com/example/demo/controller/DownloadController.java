package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ImageExtractionException;
import com.example.demo.request.DownloadRequestParams;
import com.example.demo.util.FTPDownload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description="Download de imagens compactadas")
public class DownloadController {
	private static Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	@Autowired
	FTPDownload download;
	
	private String zipFolder;
	
	@RequestMapping(value="/download", method = RequestMethod.POST, produces = "image/jpeg", consumes="application/json")
	@ApiOperation(value="Retorna a imagem extraida de um arquivo compactado")
	public ResponseEntity<Resource> download(@RequestBody DownloadRequestParams downloadParams) throws IOException{
		logger.info(String.format("IMAGE DOWNLOAD REQUEST [%s]", downloadParams.toString()));

		zipFolder = System.getProperty("ZIP_FOLDER");
		
		if (zipFolder != null && zipFolder.length() > 0){
			logger.info(String.format("Pasta temporaria para descompactar os arquivos: %s" , zipFolder));
		}else{
			logger.info(String.format("Não foi definida uma pasta para descompactar os arquivos!"));			
		}

		if (!download.download(downloadParams, zipFolder)){
			logger.error("Falha ao realizar download de arquivo.");			
		}
		
		try {
			byte[] image = extractImage(downloadParams.getZipFile(), downloadParams.getFileEntry());
			
			ByteArrayResource resource = new ByteArrayResource(image);
			
			return ResponseEntity.ok()
					.contentLength(image.length)
					.contentType(MediaType.parseMediaType("image/jpeg"))
					.body(resource);	    			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	private static byte[] getImage(InputStream in) throws IOException  {
        BufferedImage image = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", baos);
        return baos.toByteArray();
	}
	
	private byte[] extractImage(String zipFilename, String fileEntry){
		byte[] image = null;
		ZipFile zipFile = null;
		
		try {
			zipFile = new ZipFile(zipFolder + "/" + zipFilename);
			
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			
			while(entries.hasMoreElements()){
				ZipEntry entry = entries.nextElement();
				
				if (entry.getName().equals(fileEntry)){
					image = getImage(zipFile.getInputStream(entry));
					logger.info("Imagem recuperada do zip: " + fileEntry);
				}
			}
			
			if (image == null){
				logger.error(String.format("Imagem [%s] nao existe no arquivo zip.", fileEntry));
				throw new ImageExtractionException(fileEntry);
			}
		} catch (Exception e) {
			throw new ImageExtractionException(e, fileEntry);
		} finally {
			try {
				zipFile.close();
			} catch (IOException e) {}
		}
		
		return image;
	}
}