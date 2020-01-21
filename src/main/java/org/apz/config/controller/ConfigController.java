package org.apz.config.controller;

import org.apz.config.dto.ConfigDto;
import org.apz.config.service.HoconService;
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
	
	@GetMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> get() throws JsonProcessingException {
		
		try {
			
			final String opacConf = hoconService.get();
			
			return ResponseEntity.status(HttpStatus.OK).body(opacConf);
			//return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(response));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	
	@PostMapping(value="/api/register", consumes=MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestBody ConfigDto ConfigDto) throws JsonProcessingException {
		
		//final ObjectMapper mapper = new ObjectMapper();
		
		try {
			hoconService.register(ConfigDto);
			return ResponseEntity.status(HttpStatus.OK).body("ok");
			//return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(response));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	
}
