package io.github.andrijat98.printingmachineinfoapp.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.github.andrijat98.printingmachineinfoapp.models.Image;
import io.github.andrijat98.printingmachineinfoapp.models.PrintingMachine;

public class UploadImageUtil {
	
	public static Set<Image> uploadImage(MultipartFile[] multipartFiles, PrintingMachine machine) throws IOException {
		Set<Image> imageModels = new HashSet<>();
		for (MultipartFile file: multipartFiles) {
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String uploadDir = "machine-photos/" + machine.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, file);
			
			Image imageModel = new Image(
					"http://localhost:8080/" + uploadDir + "/" + fileName
					);
			imageModels.add(imageModel);
		}
		
		return imageModels;
	}
}
