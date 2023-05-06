package cp.err.sohot.service;


import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DetectionModelService {

	private final S3Service s3Service;

	public int exeModel(String s3Url) throws IOException {
		String temp = s3Url.split("sohot/")[1].replaceAll("\"}", "");
		s3Service.getObject(temp);
		return 80;
	}
}
