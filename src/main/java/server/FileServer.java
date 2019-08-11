package server;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import client.FileClient;

public class FileServer extends Thread {
	
	private ServerSocket ss;
	private String fileName;
	private int packetSize;
	
	public FileServer(int port, String fileName, int packetSize) {
		this.packetSize = packetSize;
		this.fileName = fileName;
		try {
			ss = new ServerSocket(port);
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
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] buffer = new byte[packetSize];
		
		//int filesize = 15123; // Send file size in separate msg
		int read = 0;
		int totalRead = 0;
		//int remaining = filesize;
		while((read = dis.read(buffer, 0, packetSize)) > 0) {
			totalRead += read;
			//remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}
		
		fos.close();
		dis.close();
	}
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		//InputStream input = FileServer.class.getClassLoader().getResourceAsStream("server.properties");
	    try {
			InputStream input = new FileInputStream("server.properties");
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		FileServer fs = new FileServer(Integer.valueOf(prop.getProperty("server.port")),
				prop.getProperty("server.file.path"),
				Integer.valueOf(prop.getProperty("server.packet.size")));
		fs.start();
	}

}