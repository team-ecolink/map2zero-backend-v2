package com.ecolink.core.like.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.core.auth.token.UserPrincipal;
import com.ecolink.core.common.response.ApiResponse;
import com.ecolink.core.like.dto.request.DeleteLikeRequest;
import com.ecolink.core.like.dto.request.PostLikeRequest;
import com.ecolink.core.like.service.ReviewLikeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/likes")
public class ReviewLikeController {

	private final ReviewLikeService reviewLikeService;

	@Tag(name = "${swagger.tag.review-like}")
	@Operation(summary = "리뷰 좋아요 등록 API - 인증 필요",
		description = "리뷰 좋아요 등록 - 인증 필요",
		security = {@SecurityRequirement(name = "session-token")})
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ApiResponse<Void> addReviewLike(
		@RequestBody @Valid PostLikeRequest request,
		@AuthenticationPrincipal UserPrincipal principal) {
		reviewLikeService.addReviewLike(request.reviewId(), principal.getAvatarId());
		return ApiResponse.ok();
	}

	@Tag(name = "${swagger.tag.review-like}")
	@Operation(summary = "리뷰 좋아요 취소 API - 인증 필요",
		description = "리뷰 좋아요 취소 - 인증 필요",
		security = {@SecurityRequirement(name = "session-token")})
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping
	public ApiResponse<Void> deleteReviewLike(
		@RequestBody @Valid DeleteLikeRequest request,
		@AuthenticationPrincipal UserPrincipal principal) {
		reviewLikeService.deleteReviewLike(request.reviewId(), principal.getAvatarId());
		return ApiResponse.ok();
	}

}
