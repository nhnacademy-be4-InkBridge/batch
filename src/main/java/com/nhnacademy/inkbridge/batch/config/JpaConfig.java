package com.nhnacademy.inkbridge.batch.config;

import com.nhnacademy.inkbridge.batch.property.JpaProperty;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * class: JpaConfig.
 *
 * @author jangjaehun
 * @version 2024/02/23
 */
@Configuration
@AllArgsConstructor
public class JpaConfig {

    private JpaProperty jpaProperty;
    private KeyConfig keyConfig;
    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(keyConfig.keyStore(jpaProperty.getUrl()));
        dataSource.setUsername(keyConfig.keyStore(jpaProperty.getUsername()));
        dataSource.setPassword(keyConfig.keyStore(jpaProperty.getPassword()));

        dataSource.setInitialSize(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        dataSource.setMaxTotal(20);

        dataSource.setMaxWaitMillis(20);

        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);

        return dataSource;
    }
}
