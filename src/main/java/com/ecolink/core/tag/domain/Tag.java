package com.ecolink.core.tag.domain;

import com.ecolink.core.common.domain.BaseTimeEntity;
import com.ecolink.core.tag.constant.TagCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	private String color;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TagCategory category;

	public boolean isCategoryOf(TagCategory category) {
		return this.category.equals(category);
	}

}
