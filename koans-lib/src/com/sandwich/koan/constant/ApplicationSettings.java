package com.sandwich.koan.constant;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.sandwich.util.io.directories.DirectoryManager;

public class ApplicationSettings {

	private static final ResourceBundle CONFIG_BUNDLE;
	static{
		try {
			CONFIG_BUNDLE= new PropertyResourceBundle(new FileInputStream(
				DirectoryManager.injectFileSystemSeparators(
					DirectoryManager.getProdMainDir(), "config.properties")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static boolean debug;
	
	public static void setDebug(boolean debug2) {
		debug = debug2;
	}
	
	public static boolean isDebug(){
		return debug || isEqual(CONFIG_BUNDLE.getString("debug"), true, true);
	}
	
	public static boolean isEncouragementEnabled(){
		return isEqual(CONFIG_BUNDLE.getString("enable_encouragement"), true, true);
	}
	
	public static char getExitChar(){
		return CONFIG_BUNDLE.getString("exit_character").charAt(0);
	}
	
	public static String getPathXmlFileName(){
		String tempFileName = CONFIG_BUNDLE.getString("path_xml_filename");
		int midpoint = tempFileName.contains(KoanConstants.PERIOD) ? tempFileName.lastIndexOf(KoanConstants.PERIOD) : tempFileName.length();
		String filename = tempFileName.substring(0, midpoint);
		String suffix = tempFileName.substring(midpoint, tempFileName.length());
		return new StringBuilder(filename).append("_").append(Locale.getDefault().getLanguage()).append(suffix).toString();
	}
	
	private static boolean isEqual(String value, Object e2, boolean ignoreCase){
		if(value == e2){
			return true;
		}
		if(value != null){
			String stringValue = String.valueOf(e2);
			return ignoreCase ? value.equalsIgnoreCase(stringValue) : value.equals(stringValue);
		}
		return false;
	}
	
}
