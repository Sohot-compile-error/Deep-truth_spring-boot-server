package cp.err.sohot.config;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class QuerydslConfig {
	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}
}
