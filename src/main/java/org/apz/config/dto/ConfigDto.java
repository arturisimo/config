package org.apz.config.dto;

import java.io.Serializable;
import java.util.List;

public class ConfigDto implements Serializable {
	
	private static final long serialVersionUID = 2133463596118013462L;
	
	List<PropertyDto> properties;

	public List<PropertyDto> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyDto> properties) {
		this.properties = properties;
	}
	
	
	
}
