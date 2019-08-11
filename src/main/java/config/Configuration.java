package config;

public class Configuration {
	private String host;
	private int port;
	private String file;
	private int packetSize;
	
	public Configuration(String host, int port, String file,
			int packetSize) {
		this.host = host;
		this.port = port;
		this.file = file;
		this.packetSize = packetSize;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getFile() {
		return file;
	}

	public int getPacketSize() {
		return packetSize;
	}
}
