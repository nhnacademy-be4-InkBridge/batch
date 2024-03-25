package com.nhnacademy.inkbridge.batch.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * class: JpaProperty.
 *
 * @author jangjaehun
 * @version 2024/02/23
 */
@Component
@ConfigurationProperties(prefix = "datasource")
@Setter
@Getter
public class JpaProperty {

    private String username;

    private String password;

    private String url;

}
