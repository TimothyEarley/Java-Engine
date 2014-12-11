package util;


import javax.swing.JOptionPane;

public abstract class Crash {
	
	public static void crash(Exception e, String message) {
		e.printStackTrace();
		String msg = "Sorry 'bout that: ";
		msg += message;
		msg += ", " + e.getMessage();
		crash(msg);

	}
	
	public static void crash(String msg) {
		JOptionPane.showMessageDialog(null, msg, "An Error has occured", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
	
	public static void crash(Exception e) {
		crash(e, "");
	}
	
}
