package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public abstract class Save {

	public static void save(Object o, String path) {
		File file = new File(path);
		if (!file.exists()) {
			File dir = file.getParentFile();
			if (dir != null) {
				if (!dir.isDirectory()) {
					dir.mkdirs();
				}
			}
		}

		ObjectOutputStream objOut = null;

		try {

			FileOutputStream fileOut = new FileOutputStream(file);

			objOut = new ObjectOutputStream(fileOut);

			objOut.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (objOut != null)
				try {
					objOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static Object load(String path) {
		File file = new File(path);

		if (!file.exists())
			return null;

		ObjectInputStream objIn = null;
		Object o = null;

		try {
			FileInputStream fileInput = new FileInputStream(file);
			
			objIn = new ObjectInputStream(fileInput);
			
			o = objIn.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (objIn != null)
				try {
					objIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return o;
	}

	public static void saveXML(HashMap<?, ?> map, String fileName) {
		Properties properties = new Properties();
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<?, ?> pairs = (Entry<?, ?>) it.next();
			properties.setProperty(pairs.getKey().toString(), pairs.getValue().toString());
		}

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(file);
			properties.storeToXML(os, "Keybindings");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HashMap<Object, Object> loadXML(String fileName) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			File file = new File(fileName);
			if (!file.exists())
				return null;
			
			Properties properties = new Properties();
			properties.loadFromXML(new FileInputStream(file));
			map.putAll(properties);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
}
