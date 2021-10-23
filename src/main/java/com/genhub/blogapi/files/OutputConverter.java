package com.genhub.blogapi.files;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * Converter interface for output DTO.
 *
 * <b>The implementation type must be equal to DTO type</b>
 *
 * @param <DTO>    the implementation class type
 * @param <DOMAIN> domain type
 * @author ssatwa
 */
public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {

	/**
	 * Convert from domain.(shallow)
	 *
	 * @param domain domain data
	 * @return converted dto data
	 */
	@SuppressWarnings("unchecked")
	@NonNull
	default <T extends DTO> T convertFrom(@NonNull DOMAIN domain) {

		try {
			updateProperties(domain, this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (T) this;
	}

	/**
	 * Update properties (non null).
	 *
	 * @param source source data must not be null
	 * @param target target data must not be null
	 * @throws Exception
	 * @throws BeanUtilsException if copying failed
	 */
	public static void updateProperties(@NonNull Object source, @NonNull Object target) throws Exception {
		Assert.notNull(source, "source object must not be null");
		Assert.notNull(target, "target object must not be null");

		// Set non null properties from source properties to target properties
		try {
			org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
		} catch (BeansException e) {
			throw new Exception("Failed to copy properties", e);
		}
	}

	@NonNull
	static String[] getNullPropertyNames(@NonNull Object source) {
		return getNullPropertyNameSet(source).toArray(new String[0]);
	}

	@NonNull
	static Set<String> getNullPropertyNameSet(@NonNull Object source) {

		Assert.notNull(source, "source object must not be null");
		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Object propertyValue = beanWrapper.getPropertyValue(propertyName);

			// if property value is equal to null, add it to empty name set
			if (propertyValue == null) {
				emptyNames.add(propertyName);
			}
		}

		return emptyNames;
	}
}
