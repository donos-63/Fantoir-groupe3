package fr.simplon.fantoir.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.sqlite.SQLiteDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "fr.simplon.fantoir.repositories")
@PropertySource("persistence.properties")
public class DbConfig {

    @Autowired
    private Environment env;

    // @Bean
    // public DataSource dataSource() {
    //     final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    //     dataSource.setDriverClassName(env.getProperty("driverClassName"));
    //     dataSource.setUrl(env.getProperty("url"));
    //     dataSource.setUsername(env.getProperty("user"));
    //     dataSource.setPassword(env.getProperty("password"));
    //     return dataSource;
    // }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:" + "fantoir.db");
        dataSourceBuilder.type(SQLiteDataSource.class);
        return dataSourceBuilder.build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("fr.simplon.fantoir.models");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();

        final String hibernateDialectConf = "hibernate.dialect";
        final String hibernateShowSqlConf = "hibernate.show_sql";

        if (env.getProperty(hibernateDialectConf) != null) {
            hibernateProperties.setProperty(hibernateDialectConf, env.getProperty(hibernateDialectConf));
        }
        if (env.getProperty(hibernateShowSqlConf) != null) {
            hibernateProperties.setProperty(hibernateShowSqlConf, env.getProperty(hibernateShowSqlConf));
        }
        return hibernateProperties;
    }

}
