package cp.err.sohot.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class S3Service {

	private final AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String bucket;

	public void getObject(String storedFileName) throws IOException {
		S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));

		try (InputStream inputStream = s3Object.getObjectContent()) {
			FileOutputStream outputStream = new FileOutputStream("/path/to/downloaded/file");
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			log.info("File downloaded successfully.");
		} catch (IOException e) {
			log.error("Error while downloading file: " + e.getMessage());
		}
	}
}
