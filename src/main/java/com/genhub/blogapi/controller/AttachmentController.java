package com.genhub.blogapi.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.genhub.blogapi.files.Attachment;
import com.genhub.blogapi.files.AttachmentDTO;
import com.genhub.blogapi.files.AttachmentParam;
import com.genhub.blogapi.files.AttachmentQuery;
import com.genhub.blogapi.files.AttachmentService;
import com.genhub.blogapi.files.AttachmentType;
import com.genhub.blogapi.security.CurrentUser;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.utils.AppConstants;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ssatwa Oct 17, 2021
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/attachments")
@Slf4j
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;

	@GetMapping
	public Page<AttachmentDTO> pageBy(@CurrentUser UserPrincipal currentUser,
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
			AttachmentQuery attachmentQuery) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		return attachmentService.pageDtosBy(pageable, attachmentQuery);
	}

	@GetMapping("{id:\\d+}")
	@ApiOperation("Gets attachment detail by id")
	public AttachmentDTO getBy(@CurrentUser UserPrincipal currentUser, @PathVariable("id") Integer id) {
		Attachment attachment = attachmentService.getById(id);
		return attachmentService.convertToDto(attachment);
	}

	@PutMapping("{attachmentId:\\d+}")
	@ApiOperation("Updates a attachment")
	public AttachmentDTO updateBy(@CurrentUser UserPrincipal currentUser,
			@PathVariable("attachmentId") Integer attachmentId, @RequestBody @Valid AttachmentParam attachmentParam) {
		Attachment attachment = attachmentService.getById(attachmentId);
		attachmentParam.update(attachment);
		return new AttachmentDTO().convertFrom(attachmentService.update(attachment));
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Deletes attachment permanently by id")
	public AttachmentDTO deletePermanently(@CurrentUser UserPrincipal currentUser, @PathVariable("id") String id) {
		return attachmentService.convertToDto(attachmentService.removePermanently(id));
	}

	@DeleteMapping
	@ApiOperation("Deletes attachments permanently in batch by id array")
	public List<Attachment> deletePermanentlyInBatch(@CurrentUser UserPrincipal currentUser,
			@RequestBody List<String> ids) {
		return attachmentService.removePermanently(ids);
	}

	@PostMapping("upload")
	@ApiOperation("Uploads single file")
	public AttachmentDTO uploadAttachment(@CurrentUser UserPrincipal currentUser,
			@RequestPart("file") MultipartFile file) {
		return attachmentService.convertToDto(attachmentService.upload(file));
	}

	@PostMapping(value = "uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation("Uploads multi files (Invalid in Swagger UI)")
	public List<AttachmentDTO> uploadAttachments(@RequestPart("files") MultipartFile[] files) {
		List<AttachmentDTO> result = new LinkedList<>();

		for (MultipartFile file : files) {
			// Upload single file
			Attachment attachment = attachmentService.upload(file);
			// Convert and add
			result.add(attachmentService.convertToDto(attachment));
		}

		return result;
	}

	@GetMapping("media_types")
	@ApiOperation("Lists all of media types")
	public List<String> listMediaTypes() {
		return attachmentService.listAllMediaType();
	}

	@GetMapping("types")
	@ApiOperation("Lists all of types.")
	public List<AttachmentType> listTypes() {
		return attachmentService.listAllType();
	}
}
