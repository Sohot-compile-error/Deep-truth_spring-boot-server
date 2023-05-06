package cp.err.sohot.controller;


import cp.err.sohot.dto.S3Dto;
import cp.err.sohot.service.DetectionModelService;
import java.io.IOException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DetectController {

	private final DetectionModelService detectionModelService;

	@PostMapping("/audio")
	public ResponseEntity<?> audioDetect(@RequestBody S3Dto s3Dto) throws IOException {
		System.out.println(s3Dto);
		HashMap<String, Integer> res = new HashMap<>();

		int predictionRate = detectionModelService.getPredictionRate(s3Dto.getUrl());
		res.put("predictionRate", predictionRate);
		return ResponseEntity.ok().body(res);
	}


	@PostMapping("/video")
	public ResponseEntity<?> videoDetect(@RequestBody S3Dto s3Dto) throws IOException {
		System.out.println(s3Dto);
		HashMap<String, Integer> res = new HashMap<>();

		int predictionRate = detectionModelService.getPredictionRate(s3Dto.getUrl());
		res.put("predictionRate", predictionRate);
		return ResponseEntity.ok().body(res);
	}



}
