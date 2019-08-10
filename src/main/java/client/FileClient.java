package client;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class FileClient {
	
	private Socket s;
	
	public FileClient(String host, int port, String file, int packetSize) {
		try {
			s = new Socket(host, port);
			sendFile(file, packetSize);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void sendFile(String file, int packetSize) throws IOException {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[packetSize];
		
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
		
		fis.close();
		dos.close();	
	}
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream input = FileClient.class.getClassLoader().getResourceAsStream("client.properties");
	    try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileClient fc = new FileClient(prop.getProperty("client.address"),
										Integer.valueOf(prop.getProperty("client.port")),
										prop.getProperty("client.file.path"),
										Integer.valueOf(prop.getProperty("client.packet.size")));
	}

}