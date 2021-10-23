package com.genhub.blogapi.files;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import org.springframework.lang.Nullable;

/**
 * Converter interface for input DTO.
 *
 * @author ssatwa
 */
public interface InputConverter<DOMAIN> {

    /**
     * Convert to domain.(shallow)
     *
     * @return new domain with same value(not null)
     */
    @SuppressWarnings("unchecked")
    default DOMAIN convertTo() {
        // Get parameterized type
        ParameterizedType currentType = parameterizedType();

        // Assert not equal
        Objects.requireNonNull(currentType, "Cannot fetch actual type because parameterized type is null");

        Class<DOMAIN> domainClass = (Class<DOMAIN>) currentType.getActualTypeArguments()[0];

        try {
			return BeanUtils.transformFrom(this, domainClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

    /**
     * Update a domain by dto.(shallow)
     *
     * @param domain updated domain
     */
    default void update(DOMAIN domain) {
        try {
			BeanUtils.updateProperties(this, domain);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Get parameterized type.
     *
     * @return parameterized type or null
     */
    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());
    }
}

