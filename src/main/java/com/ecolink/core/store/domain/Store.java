package com.ecolink.core.store.domain;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

import com.ecolink.core.common.constant.Address;
import com.ecolink.core.common.domain.BaseTimeEntity;
import com.ecolink.core.event.domain.Event;
import com.ecolink.core.file.domain.MultiPhotoContainer;
import com.ecolink.core.manager.domain.StoreRegistration;
import com.ecolink.core.product.domain.StoreProduct;
import com.ecolink.core.review.domain.Review;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "store", indexes = @Index(name = "idx_store_name", columnList = "name"))
public class Store extends BaseTimeEntity implements MultiPhotoContainer<StorePhoto> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@Column(length = 1000)
	private String description;

	private String summary;

	private String contact;

	private String homepageUrl;

	private String instagramUrl;

	private String naverMapUrl;

	private int bookmarkCnt;

	private int reviewCnt;

	private int productCnt;

	private int totalScore;

	@Valid
	@NotNull
	@Embedded
	private Address address;

	private Point coordinates;

	@Nullable
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_registration_id")
	private StoreRegistration storeRegistration;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StorePhoto> photos = new ArrayList<>();

	@OneToMany(mappedBy = "store")
	private List<StoreProduct> storeProducts = new ArrayList<>();

	@OneToMany(mappedBy = "store")
	private List<Event> events = new ArrayList<>();

	@OneToMany(mappedBy = "store")
	private List<StoreOperatingHours> storeOperatingHour = new ArrayList<>();

	@OneToMany(mappedBy = "store")
	private List<StoreTag> storeTags = new ArrayList<>();

	public double averageScore() {
		if (reviewCnt == 0)
			return 0;
		return (double)totalScore / reviewCnt;
	}

	public double roundedAverageScore() {
		return Math.round(averageScore() * 10.0) / 10.0;
	}

	public void addBookmarkCount() {
		this.bookmarkCnt++;
	}

	public void deleteBookmarkCount() {
		if (this.bookmarkCnt <= 0) {
			throw new IllegalStateException("북마크 수는 음수가 될 수 없습니다.");
		}
		this.bookmarkCnt--;
	}

	public void addReview(Review review) {
		this.reviewCnt++;
		this.totalScore += review.getScore();
	}

	public void subtractReview(Review review) {
		if (this.reviewCnt <= 0) {
			throw new IllegalStateException("리뷰 수는 음수가 될 수 없습니다.");
		}
		this.reviewCnt--;
		this.totalScore -= review.getScore();
	}

	public void addProductCnt() {
		this.productCnt++;
	}

}
