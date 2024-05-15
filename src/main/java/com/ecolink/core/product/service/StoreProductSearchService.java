package com.ecolink.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecolink.core.auth.token.UserPrincipal;
import com.ecolink.core.common.dto.CursorPage;
import com.ecolink.core.common.error.ErrorCode;
import com.ecolink.core.common.error.exception.ManagerForbiddenException;
import com.ecolink.core.common.util.AuthorityUtil;
import com.ecolink.core.product.dto.request.GetStoreProductRequest;
import com.ecolink.core.product.dto.response.GetStoreProductResponse;
import com.ecolink.core.tag.constant.TagCategory;
import com.ecolink.core.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StoreProductSearchService {

	private final StoreProductService storeProductService;
	private final TagService tagService;

	public CursorPage<GetStoreProductResponse, Long> searchStoreProducts(Long storeId, GetStoreProductRequest request) {
		if (request.getTagId() != null)
			tagService.validateCategory(request.getTagId(), TagCategory.PRODUCT);

		return CursorPage.of(storeProductService.getByNameAndTag(storeId, request, true), request.getSize(),
			GetStoreProductResponse::getId);
	}

	public CursorPage<GetStoreProductResponse, Long> searchStoreProductsByManager(Long storeId,
		GetStoreProductRequest request, UserPrincipal principal) {
		if(!principal.isManagerOf(storeId) && !AuthorityUtil.hasAdminAuthority(principal))
			throw new ManagerForbiddenException(ErrorCode.NOT_MANAGER_OF_STORE);

		if (request.getTagId() != null)
			tagService.validateCategory(request.getTagId(), TagCategory.PRODUCT);

		return CursorPage.of(storeProductService.getByNameAndTag(storeId, request, null), request.getSize(),
			GetStoreProductResponse::getId);
	}

}
