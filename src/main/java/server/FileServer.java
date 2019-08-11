package server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import config.Configuration;
import config.ConfigurationReader;
import config.ServerConfigurationReader;

public class FileServer extends Thread {
	
	private ServerSocket ss;
	private Configuration configuration;
	
	public FileServer(Configuration configuration) {
		this.configuration = configuration;
		connect();
	}
	
	private void connect() {
		try {
			ss = new ServerSocket(configuration.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				Socket clientSock = ss.accept();
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

		while((read = dis.read(buffer, 0, configuration.getPacketSize())) > 0) {
			totalRead += read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}
		
		fos.close();
		dis.close();
	}
	
	public static void main(String[] args) {
		ConfigurationReader configReader = new ServerConfigurationReader("server.properties");
		FileServer fs = new FileServer(configReader.getConfiguration());
		fs.start();
	}

}