package com.ecolink.core.health.controller;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.core.health.dto.response.HealthCheckResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Health check", description = "서버 상태 체크 API")
@RequiredArgsConstructor
@RestController
public class HealthCheckController {

	private final Environment environment;

	@Tag(name = "Health check")
	@Operation(summary = "서버 Health Check API", description = "현재 서버가 정상적으로 기동이 된 상태인지 검사하는 API")
	@GetMapping("/health")
	public HealthCheckResponse healthCheck() {
		return HealthCheckResponse.ok(Arrays.asList(environment.getActiveProfiles()));
	}

}
