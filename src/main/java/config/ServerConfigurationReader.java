package config;

import java.util.Properties;

public class ServerConfigurationReader extends ConfigurationReader {
	
	public ServerConfigurationReader(String fileName) {
		super(fileName);
	}

	public Configuration getConfiguration() {
		Properties prop = loadProperties();
	    
	    return new Configuration(prop.getProperty("server.address"),
									   Integer.valueOf(prop.getProperty("server.port")),
									   prop.getProperty("server.file.path"),
									   Integer.valueOf(prop.getProperty("server.packet.size")));
	}
}
