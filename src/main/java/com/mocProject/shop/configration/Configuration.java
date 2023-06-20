package com.mocProject.shop.configration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "shop")
public class Configuration {

    private String servicename;


}
