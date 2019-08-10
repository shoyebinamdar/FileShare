package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	public void load(String filename) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

		      input = getClass().getClassLoader().getResourceAsStream("config.properties");


		    // load a properties file
		    prop.load(input);

		    // get the property value and print it out
		    System.out.println(prop.getProperty("database"));
		    System.out.println(prop.getProperty("dbuser"));
		    System.out.println(prop.getProperty("dbpassword"));

		} catch (IOException ex) {
		    ex.printStackTrace();
		} finally {
		    if (input != null) {
		        try {
		            input.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	
}
