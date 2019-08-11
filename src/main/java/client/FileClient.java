package client;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import server.FileServer;

import config.ClientConfigurationReader;
import config.Configuration;
import config.ConfigurationReader;

public class FileClient {
	static final Logger logger = Logger.getLogger(FileServer.class);

	private Socket socket;
	private Configuration configuration;
	
	public FileClient(Configuration configuration) {
		this.configuration = configuration;
		connect();
		sendFile();
	}
	
	private void connect() {
		try {
			logger.info("Creating client socket on " + configuration.getHost() + ":" + configuration.getPort());
			socket = new Socket(configuration.getHost(), configuration.getPort());
			logger.info("Successfully created client socket");
		} catch (UnknownHostException e) {
			logger.error("Failed to create client socket. " + configuration.getHost() + " not found.");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Failed to create client socket.");
			e.printStackTrace();
		}
	}
	
	private void sendFile() {
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			FileInputStream fis = new FileInputStream(configuration.getFile());
			byte[] buffer = new byte[configuration.getPacketSize()];
			int read = 0;
			int totalWrite = 0;
			
			logger.info("Now sending the contents of " + configuration.getFile() + " to server");
			while ((read = fis.read(buffer)) > 0) {
				totalWrite += read;
				dos.write(buffer);
			}
			logger.info("Successfully sent " + totalWrite + " bytes of data to server");
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