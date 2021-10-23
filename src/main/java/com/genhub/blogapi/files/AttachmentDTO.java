package com.genhub.blogapi.files;

import java.util.Date;

import lombok.Data;

/**
 * Attachment output dto.
 *
 * @author ssatwa
 * @date 3/21/19
 */
@Data
public class AttachmentDTO implements OutputConverter<AttachmentDTO, Attachment> {

    private Integer id;

    private String name;

    private String path;

    private String fileKey;

    private String thumbPath;

    private String mediaType;

    private String suffix;

    private Integer width;

    private Integer height;

    private Long size;

    private AttachmentType type;

    private Date createTime;
}
