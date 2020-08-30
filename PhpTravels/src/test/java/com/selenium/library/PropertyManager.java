package com.selenium.library;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyManager {

	final static Logger logger = Logger.getLogger(PropertyManager.class);
	private String propertiesFile;
	private OutputStream output;
	private InputStream input;
	private Properties prop;

	public PropertyManager(String propertiesFilePath) {
		propertiesFile = propertiesFilePath;
		prop = new Properties();
	}

	public String readProperty(String key) {
		String value= null;
		try {
			input = new FileInputStream(propertiesFile);
			prop.load(input);
			value=prop.getProperty(key); 
		} catch (Exception e) {
          logger.error("Error :",e);
		}finally {
			try {
				if(input != null) {
				input.close();}

			} catch (Exception e) {
				logger.error("Error: ", e);
				assertTrue(false);
			}
		}
		
		return value;
	}

	public void SetProperty(String value,String key) {
	try{output = new FileOutputStream(propertiesFile);
	prop.setProperty(key, value);
	prop.store(output, null);	
	} catch (Exception e) {
		logger.error("Error: ", e);
		
	} finally {
		try {
			
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
	}}}
	
	public void SetProperty(String value,String key,String comment) {
		try{output = new FileOutputStream(propertiesFile);
		prop.setProperty(key, comment);
		prop.store(output, null);	
		} catch (Exception e) {
			logger.error("Error: ", e);
			
		} finally {
			try {
				
			} catch (Exception e) {
				logger.error("Error: ", e);
				assertTrue(false);
		}}}
}
	
