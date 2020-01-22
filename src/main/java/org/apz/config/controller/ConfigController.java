package org.apz.config.controller;

import org.apz.config.dto.ConfigDto;
import org.apz.config.service.HoconService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ConfigController {
	
	@Autowired
	HoconService hoconService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);
	
	@GetMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> get() throws JsonProcessingException {
		
		try {
			final String opacConf = hoconService.renderJson();
			return ResponseEntity.status(HttpStatus.OK).body(opacConf);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping(value="/api/register", consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestBody ConfigDto ConfigDto) throws JsonProcessingException {
		try {
			hoconService.register(ConfigDto);
			final String opacConf = hoconService.renderJson();
			LOG.info("actualización correcta");
			return ResponseEntity.status(HttpStatus.OK).body(opacConf);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
