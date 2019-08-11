package config;

import java.util.Properties;

public class ClientConfigurationReader extends ConfigurationReader {
		
	public ClientConfigurationReader(String fileName) {
		super(fileName);
	}

	public Configuration getConfiguration() {
		Properties prop = loadProperties();
		
	    return new Configuration(prop.getProperty("client.address"),
									   Integer.valueOf(prop.getProperty("client.port")),
									   prop.getProperty("client.file.path"),
									   Integer.valueOf(prop.getProperty("client.packet.size")));
	}
}
