package server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;

import config.Configuration;
import config.ConfigurationReader;
import config.ServerConfigurationReader;

public class FileServer extends Thread {
	static final Logger logger = Logger.getLogger(FileServer.class);

	
	private ServerSocket serverSocket;
	private Configuration configuration;
	
	public FileServer(Configuration configuration) {
		this.configuration = configuration;
		connect();
	}
	
	private void connect() {
		try {
			logger.info("Creating server socket");
			serverSocket = new ServerSocket(configuration.getPort());
			logger.info("Server socket created successfully");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Failed to create server socket");
		}
	}
	
	public void run() {
		while (true) {
			try {
				logger.info("Server socket trying to accept connections on port:" + configuration.getPort());
				Socket clientSock = serverSocket.accept();
				logger.info("Server socket ready to accept data from port:" + configuration.getPort());
				logger.info("Waiting for data");
				saveFile(clientSock);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveFile(Socket clientSock) throws IOException {
		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		FileOutputStream fos = new FileOutputStream(configuration.getFile());
		byte[] buffer = new byte[configuration.getPacketSize()];
		
		int read = 0;
		int totalRead = 0;

		if (dis.available() > 0)
			logger.info("Data available. Now reading dataa from stream and writing to output file : " + configuration.getFile());
		while((read = dis.read(buffer, 0, configuration.getPacketSize())) > 0) {
			totalRead += read;
			fos.write(buffer, 0, read);
		}
		
		if (totalRead > 0)
			logger.info("Successfully written " + totalRead + " bytes of data to " + configuration.getFile());
		
		fos.close();
		dis.close();
	}
	
	public static void main(String[] args) {
		ConfigurationReader configReader = new ServerConfigurationReader("server.properties");
		FileServer fs = new FileServer(configReader.getConfiguration());
		fs.start();
	}
}