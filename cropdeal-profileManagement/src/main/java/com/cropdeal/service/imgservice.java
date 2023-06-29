package com.cropdeal.service;

import java.io.IOException;
import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cropdeal.entites.ImageModel;
import com.cropdeal.repositry.imagerepostyry;
import com.cropdeal.util.imageutil;

@Service
public class imgservice {

	
	@Autowired
	private imagerepostyry imageRepository;
	
	public String uploadimg(MultipartFile imgFile) throws IOException {
//		imgFile.getBytes().length;
	    
		ImageModel imageModel=	imageRepository.save(ImageModel.builder().name(imgFile.getOriginalFilename())
					.type(imgFile.getContentType())
					.picByte(imageutil.compressImage(imgFile.getBytes())).build());
		
		if(imageModel!=null) {
			
			return "file uploaded";
		}
		
		
		return "file upload failed";
		
	}
	
	public byte[] downloadimg(String imgname) {
		
	Optional<ImageModel> imOptional= imageRepository.findByName(imgname);
	return imageutil.decompressImage(imOptional.get().getPicByte());
		
	}
	public ImageModel downloadimgclass(String imgname) {
		
		Optional<ImageModel> imOptional= imageRepository.findByName(imgname);
		
		return imOptional.get().builder().picByte(imageutil.decompressImage(imOptional.get().getPicByte())).build();
			
		}
}
