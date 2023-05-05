package cp.err.sohot.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger3Config {
	@Bean
	public GroupedOpenApi publicApi(){
		return GroupedOpenApi.builder()
			.group("compile error")
			.pathsToMatch("/**")
			.build();
	}

	@Bean
	public OpenAPI springShopOpenAPI(){

		Info info = new Info()
			.title("So hot 해커톤 team compile err  API")
			.description("So hot 해커톤 team compile err v1 API 명세서입니다.")
			.version("v0.0.1");

		return new OpenAPI()
			.info(info);
	}

}
