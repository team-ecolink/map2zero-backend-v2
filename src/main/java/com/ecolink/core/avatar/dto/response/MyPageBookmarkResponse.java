package com.ecolink.core.avatar.dto.response;

import com.ecolink.core.bookmark.domain.Bookmark;
import com.ecolink.core.common.constant.Address;
import com.ecolink.core.common.domain.ImageFile;
import com.ecolink.core.store.domain.Store;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyPageBookmarkResponse {
	@Schema(description = "북마크 ID", example = "1")
	private final Long bookmarkId;
	@Schema(description = "매장 ID", example = "1")
	private final Long id;
	@Schema(description = "매장 이름", example = "에코 상점")
	private final String name;
	@Schema(description = "매장 주소", example = "서울시 서대문구")
	private final Address address;
	@Schema(description = "매장 홍보 멘트", example = "에코 상점은 환경을 위한 다양한 제품을 판매합니다.")
	private final String summary;
	@Schema(description = "매장 사진")
	private final ImageFile photo;

	@Schema(description = "매장 평점", example = "4.8")
	private final double averageScore;

	@Schema(description = "매장 리뷰 수", example = "5")
	private final int reviewCnt;

	@QueryProjection
	public MyPageBookmarkResponse(Bookmark bookmark, Store store, ImageFile photo) {
		this.bookmarkId = bookmark.getId();
		this.id = store.getId();
		this.name = store.getName();
		this.address = store.getAddress();
		this.summary = store.getSummary();
		this.photo = photo;
		this.averageScore = store.roundedAverageScore();
		this.reviewCnt = store.getReviewCnt();
	}

}
