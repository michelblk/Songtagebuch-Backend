package de.michelblank.songtagebuch.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppConfig {
    private String frontendHomeUrl;
}
