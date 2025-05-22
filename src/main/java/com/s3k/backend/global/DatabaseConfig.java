package com.s3k.backend.global;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

  private final DataSource dataSource;

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {

    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);

    org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
    config.setMapUnderscoreToCamelCase(true);
    sqlSessionFactoryBean.setConfiguration(config);

    Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources(
        "classpath:/mapper/*.xml");
    sqlSessionFactoryBean.setMapperLocations(mapperLocations);

    return sqlSessionFactoryBean.getObject();
  }
}
