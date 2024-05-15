package com.ecolink.core.admin.dto;

import com.ecolink.core.common.domain.ImageFile;
import com.ecolink.core.product.domain.StoreProduct;
import com.ecolink.core.product.domain.StoreProductPhoto;
import com.ecolink.core.product.util.PriceUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductInfoDto {

	@Schema(description = "제품명", example = "연필")
	private final String name;

	@Schema(description = "단위당 가격", example = "10,000원")
	private final String price;

	@Schema(description = "제품 대표 사진")
	private final ImageFile photo;

	@Schema(description = "매장 ID", example = "1")
	private final Long storeId;

	@Schema(description = "매장 이름", example = "지구샵 연남점")
	private final String storeName;

	public static ProductInfoDto of(StoreProduct storeProduct, StoreProductPhoto photo) {
		return ProductInfoDto.builder()
			.name(storeProduct.getProduct().getName())
			.price(PriceUtil.formatPrice(storeProduct.getPrice()))
			.photo(photo != null ? photo.getFile() : null)
			.storeId(storeProduct.getStore().getId())
			.storeName(storeProduct.getStore().getName())
			.build();
	}
}
