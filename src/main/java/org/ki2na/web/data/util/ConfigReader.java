package org.ki2na.web.data.util;

import java.io.IOException;

import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reader for configurations file. The property file security.ini should be placed into the
 * resources folder.
 * 
 * @author Emir Munoz (emir@emunoz.org)
 * @version 0.1.2
 * 
 */
public class ConfigReader
{

	private static final transient Logger log = LoggerFactory.getLogger(ConfigReader.class.getName());
	/** INI properties file */
	private static Ini ini = null;

	/**
	 * Get the INI file instance.
	 * 
	 * @return A singleton instance of the INI file.
	 */
	private synchronized static Ini getInstance()
	{
		if (ini == null)
		{
			try
			{
				ini = new Ini(ConfigReader.class.getResourceAsStream("/web-data.ini"));
			} catch (IOException e)
			{
				log.warn("Error reading security.ini file in the resource folder.");
			}
		}

		return ini;
	}

	/**
	 * Get a property (option) from a given section.
	 * 
	 * @param section Section selected.
	 * @param option Option to retrieve.
	 * @return The value of the option in the file.
	 */
	public static String getProperty(String section, String option)
	{
		return getInstance().get(section, option);
	}

	/**
	 * Main test.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println(ConfigReader.getProperty("main", "version"));
	}

}
