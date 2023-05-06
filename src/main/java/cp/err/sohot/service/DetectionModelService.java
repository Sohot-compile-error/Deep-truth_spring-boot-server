package cp.err.sohot.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DetectionModelService {

	private String s3Url;

	private String storedFileName;

	private String objectType;

	private final S3Service s3Service;

	@Value("${file.path}")
	private String dir;

	@Value("${file.videoModel}")
	private String videoModel;

	@Value("${file.audioModel}")
	private String audioModel;

	public int getPredictionRate(String s3Url) throws IOException {
		this.s3Url = s3Url;
		storedFile();
		int predictionRate = runModel();
		return predictionRate;
	}

	public int runModel() throws IOException {
		String[] command = {"python", dir + (objectType.equals("video") ? videoModel : audioModel), "--feature_classname wave", "--model_classname TSSD", "--restore", "--eval_only"};
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = "-1";
			while (true) {
				String temp = in.readLine();
				if (temp == null) break;
				s = temp;
				log.info("log :::::::::" + s);
			}

			int predictionRate = Integer.parseInt(s);
//			deleteFile();
			return predictionRate;
		} catch (Exception e) {
			log.info("run Fail!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			log.error(Arrays.toString(e.getStackTrace()));
//			deleteFile();
			return -1;
		}
	}

	private void deleteFile() {
		File file = new File(dir + objectType + "/" + objectType + (objectType.equals("audio") ? ".wav" : ".mp4"));
		file.delete();
	}

	private void storedFile() {
		setStoredFileName();
		setFileType();
		s3Service.getObject(this.objectType, this.storedFileName);
	}

	private void setStoredFileName() {
		this.storedFileName = this.s3Url.split("sohot/")[1].replaceAll("\"}", "");
	}

	private void setFileType() {
		if (this.storedFileName.contains("wav")) this.objectType = "audio";
		if (this.storedFileName.contains("mp4")) this.objectType = "video";
	}
}
