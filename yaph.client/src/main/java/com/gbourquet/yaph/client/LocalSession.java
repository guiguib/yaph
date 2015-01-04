package com.gbourquet.yaph.client;

import java.util.HashMap;
import java.util.Map;

public class LocalSession {
	
	private Map<String, Object> attributes;
	private static final LocalSession instance = new LocalSession();
	
	private LocalSession()
	{
		attributes = new HashMap<String, Object>();
	}
	
	public Object getAttribute(String key)
	{
		return attributes.get(key);
	}
	
	public void setAttribute(String key, Object value)
	{
		this.attributes.put(key, value);
	}

	public static LocalSession getInstance() {
		return instance;
	}
	
}
