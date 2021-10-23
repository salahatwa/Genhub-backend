package com.genhub.blogapi.files;

/**
 * Attach origin.
 *
 * @author ssatwa
 * @date 2019-03-12
 */
public enum AttachmentType implements ValueEnum<Integer> {

	/**
	 * Server
	 */
	LOCAL(0),

	/**
	 * Take another cloud
	 */
	UPOSS(1),

	/**
	 * Qiniu Cloud
	 */
	QINIUOSS(2),

	/**
	 * sm.ms
	 */
	SMMS(3),

	/**
	 * Ali Cloud
	 */
	ALIOSS(4),

	/**
	 * Baidu cloud
	 */
	BAIDUBOS(5),

	/**
	 * Tencent Cloud
	 */
	TENCENTCOS(6),

	/**
	 * HUAWEI CLOUD
	 */
	HUAWEIOBS(7),

	/**
	 * MINIO
	 */
	MINIO(8),

	CLOUDINARY(9);

	private final Integer value;

	AttachmentType(Integer value) {
		this.value = value;
	}

	/**
	 * Get enum value.
	 *
	 * @return enum value
	 */
	@Override
	public Integer getValue() {
		return value;
	}
}
