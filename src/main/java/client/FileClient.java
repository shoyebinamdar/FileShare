package client;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import config.ClientConfigurationReader;
import config.Configuration;
import config.ConfigurationReader;

public class FileClient {
	
	private Socket s;
	private Configuration configuration;
	
	public FileClient(Configuration configuration) {
		this.configuration = configuration;
		connect();
		sendFile();
	}
	
	private void connect() {
		try {
			s = new Socket(configuration.getHost(), configuration.getPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendFile() {
		try {
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			FileInputStream fis = new FileInputStream(configuration.getFile());
			byte[] buffer = new byte[configuration.getPacketSize()];
			
			while (fis.read(buffer) > 0) {
				dos.write(buffer);
			}
			
			fis.close();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		ConfigurationReader configReader = new ClientConfigurationReader("client.properties");
		FileClient fc = new FileClient(configReader.getConfiguration());
	}

}