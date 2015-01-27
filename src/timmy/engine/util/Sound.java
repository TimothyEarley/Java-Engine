package timmy.engine.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import org.pscode.xui.sound.bigclip.BigClip;

public class Sound {

	public static boolean initalized = false;

	public static boolean silent = false;

	/**
	 * Durch eine Liste kann der clip gleichzeitig gespielt werden
	 */
	private ArrayList<Clip> clip = new ArrayList<Clip>();
	private String path;
	private boolean big;

	public Sound(String path) {
		this(path, true);
	}

	public Sound(String path, boolean big) {

		this.big = big;
		this.path = path;

	}

	public void start(Applet applet) {
		try {
			AudioClip clip = applet.getAudioClip(applet.getDocumentBase(), path.replaceFirst("/", ""));
			clip.play();
		} catch (Exception e) {
			Crash.crash(e, "Sound Fehler");
		}
		cleanUp();
	}

	public void start() {
		try {
			Clip clip = getClip();
			clip.open(getAudioInoutStream(path));
			clip.start();
		} catch (Exception e) {
			Crash.crash(e, "Sound Fehler");
		}
		cleanUp();
	}

	private static AudioInputStream getAudioInoutStream(String path) throws Exception {
		BufferedInputStream myStream = new BufferedInputStream(Sound.class.getResourceAsStream(path));
		return AudioSystem.getAudioInputStream(myStream);
	}

	/**
	 * Haelt die Clipliste so gering wie moeglich
	 */
	private void cleanUp() {
		for (Iterator<Clip> it = clip.iterator(); it.hasNext();)
			if (!it.next().isActive())
				it.remove();

	}

	/**
	 * erstellt einen neuen clip
	 * 
	 * @return einen neuen clip
	 * @throws LineUnavailableException
	 */
	private Clip getClip() throws LineUnavailableException {
		if (big) {
			clip.add(new BigClip(AudioSystem.getClip()));
		} else {
			clip.add(AudioSystem.getClip());
		}
		return clip.get(clip.size() - 1);
	}

	public void stop() {
		for (int i = 0; i < clip.size(); i++) {
			clip.get(i).close();
			clip.remove(i);
		}
	}

	public void loop() {
		try {
			Clip clip = getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(path)));
			// unendlich funktioniert nicht
			clip.loop(Integer.MAX_VALUE);
		} catch (Exception e) {
			Crash.crash(e, "Sound Fehler");
		}
	}

}
