package cp.err.sohot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cp.err.sohot.dto.CommonResponseDto;
import cp.err.sohot.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/health")@Tag(name = "0. 서버 상태 확인", description = "서버 상태 확인에 대한 API")
public class TestController {

	@Operation(summary = "상태 확인 api", description = "스프링 서버에 대한 상태를 확인한다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "서버 정상",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CommonResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "잘못된 요청",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "서버 내부 에러",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ErrorResponseDto.class) ))
		})
	@GetMapping
	public ResponseEntity<?> healthCheck(
		@Parameter(description = "조회한 사람 이름",required = false) @RequestParam String name
	) {

		log.debug("[healthCheck] name : {}", name);

		CommonResponseDto<Object> responseDto =
			CommonResponseDto.builder().message("server ok").build();
		return ResponseEntity.ok()
			.body(responseDto);
	}
}
