package br.com.eventos;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {

	// método de conexão com o PostgreSQL no Heroku
	// @Bean
	// public BasicDataSource dataSource() throws URISyntaxException {
	// URI dbUri = new URI(System.getenv("DATABASE_URL"));
	//
	// String username = dbUri.getUserInfo().split(":")[0];
	// String password = dbUri.getUserInfo().split(":")[1];
	// String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort()
	// + dbUri.getPath() + "?sslmode=require";
	//
	// BasicDataSource basicDataSource = new BasicDataSource();
	// basicDataSource.setUrl(dbUrl);
	// basicDataSource.setUsername(username);
	// basicDataSource.setPassword(password);
	//
	// return basicDataSource;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/db_eventos");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true); // criação de tabelas automaticamente pelo Hibernate
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		adapter.setPrepareConnection(true);
		return adapter;
	}
}
