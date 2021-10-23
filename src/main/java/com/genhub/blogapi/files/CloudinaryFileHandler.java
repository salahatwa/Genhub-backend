package com.genhub.blogapi.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ssatwa
 *
 */
@Slf4j
@Component
public class CloudinaryFileHandler implements FileHandler {

	@Override
	public UploadResult upload(MultipartFile file) {
		Assert.notNull(file, "CLOUDINARY Multipart file must not be null");
		Map map = null;
		try {
			Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "genhub", "api_key",
					"352784882937424", "api_secret", "PFUAWEMOS4Z2u7iESVei_j7rSRU"));

			map = cloudinary.uploader().upload(convertMultiPartToFile(file),
					ObjectUtils.asMap("resource_type", "auto"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		String subFilePath = map.get("url").toString();
		String public_id = map.get("public_id").toString();

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		UploadResult uploadResult = new UploadResult();
		uploadResult.setFilename(FilenameUtils.getBasename(Objects.requireNonNull(file.getOriginalFilename())));
		uploadResult.setFilePath(subFilePath);
		uploadResult.setKey(public_id);
		uploadResult.setSuffix(FilenameUtils.getExtension(file.getOriginalFilename()));
		uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
		uploadResult.setSize(file.getSize());

		// Handle thumbnail
		handleImageMetadata(file, uploadResult, () -> {
			if (ImageUtils.EXTENSION_ICO.equals(extension)) {
				return subFilePath;
			} else {
				return subFilePath;
			}
		});

		return uploadResult;
	}

	@Override
	public void delete(String key) {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "genhub", "api_key",
				"352784882937424", "api_secret", "PFUAWEMOS4Z2u7iESVei_j7rSRU"));

		try {
			cloudinary.uploader().destroy(key, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public AttachmentType getAttachmentType() {
		return AttachmentType.CLOUDINARY;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
