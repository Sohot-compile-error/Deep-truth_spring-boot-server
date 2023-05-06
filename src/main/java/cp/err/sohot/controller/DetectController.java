package cp.err.sohot.controller;


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
	public ResponseEntity<?> audioDetect(@RequestBody String s3Url) throws IOException {
		log.info("===================="+s3Url);
		log.info("return : " + detectionModelService.exeModel(s3Url));
		HashMap<String, Integer> returnOBJ = new HashMap<>();
		returnOBJ.put("predictionRate", 80);
		return ResponseEntity.ok().body(80);
	}


	@PostMapping("/video")
	public ResponseEntity<?> videoDetect(@RequestBody String s3Url) {
		HashMap<String, Integer> returnOBJ = new HashMap<>();
		returnOBJ.put("predictionRate", 80);
		return ResponseEntity.ok().body(80);
	}



}
