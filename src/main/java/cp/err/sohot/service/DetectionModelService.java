package cp.err.sohot.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DetectionModelService {

	private String s3Url;

	private String storedFileName;

	private String fileType;

	private final S3Service s3Service;

	@Value("${file.path}")
	private String dir;

	public int getPredictionRate(String s3Url) throws IOException {
		this.s3Url = s3Url;
		storedFile();
		int predictionRate = runModel();
		return predictionRate;
	}

	public int runModel() throws IOException {
		String[] command = {"python", dir + "exec.py"};
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			int predictionRate = -1;
			while ((s = in.readLine()) != null) {
				predictionRate = Integer.parseInt(s);
			}
			return predictionRate;
		} catch (Exception e) {
			return -1;
		}
	}

	private void storedFile() {
		setStoredFileName();
		setFileType();
		s3Service.getObject(this.fileType, this.storedFileName);
	}

	private void setStoredFileName() {
		this.storedFileName = this.s3Url.split("sohot/")[1].replaceAll("\"}", "");
	}

	private void setFileType() {
		if (this.storedFileName.contains("wav")) this.fileType = "audio";
		if (this.storedFileName.contains("mp4")) this.fileType = "video";
	}
}
