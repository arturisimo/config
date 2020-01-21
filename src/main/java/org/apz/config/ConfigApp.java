package org.apz.config;

import java.io.File;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@SpringBootApplication
@ComponentScan(basePackages = {"org.apz.config.service","org.apz.config.controller"})
public class ConfigApp {

	public static void main(String[] args) {
		SpringApplication.run(ConfigApp.class, args);
	}
	
	@Bean
    public Config config() {
		URL urlConfigFile = ConfigApp.class.getClassLoader().getResource("opac.conf");
		File configFile = new File(urlConfigFile.getFile());
		Config config = ConfigFactory.parseFile(configFile);
		return config.resolve();
    }
	
}
