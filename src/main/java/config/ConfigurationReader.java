package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ConfigurationReader {
	private String propertiesFileName;

	public ConfigurationReader(String fileName) {
		this.propertiesFileName = fileName;
	}
	
	public Properties loadProperties() {
		Properties prop = new Properties();
		//InputStream input = FileClient.class.getClassLoader().getResourceAsStream("client.properties");
	    try {
	    	InputStream input = new FileInputStream(propertiesFileName);
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return prop;
	}
	
	public abstract Configuration getConfiguration(); 
}
