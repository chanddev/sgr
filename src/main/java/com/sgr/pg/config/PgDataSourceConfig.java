package com.sgr.pg.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "postgresEntityManagerFactory", 
		basePackages = {"com.sgr.pg"},transactionManagerRef = "postgresTransactionManager")
public class PgDataSourceConfig {

	@Primary
	@Bean(name = "postgresDatasource")
	public DataSource dataSource() {

		return DataSourceBuilder
				.create()
				.username("nspdjmrazittad")
				.password("67815c31990a45c3b7308b01be5577b6e58f25c6e2ab78f6c5ed4387b2b89e2a")
				.url("jdbc:postgresql://ec2-18-210-159-154.compute-1.amazonaws.com:5432/d7ruc9pt0bq54i")
				.driverClassName("org.postgresql.Driver")
				.build();
	}

	@Bean(name = "postgresEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
	EntityManagerFactoryBuilder builder, @Qualifier("postgresDatasource") DataSource dataSource) {
		Map < String,
		Object > properties = new HashMap < >();
		//properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
		//properties.put("hibernate.hbm2ddl.import_files", "postgres-initial-data.sql");
		//properties.put("hibernate.default_schema", "public");
		return builder.dataSource(dataSource).properties(properties).packages("com.sgr.pg.model").build();
	}

	@Bean(name = "postgresTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public JdbcTemplate jdbcTemplatePostgres(@Qualifier("postgresDatasource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}