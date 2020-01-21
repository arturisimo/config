package org.apz.config.dto;

import java.io.Serializable;

public class PropertyDto implements Serializable  {
	
	private static final long serialVersionUID = 1846525431454494272L;
	
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
