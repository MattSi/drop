package com.badlogic.drop.desktop;

import com.badlogic.drop.ch05.homework.HomeworkGame;
import com.badlogic.drop.ch05.starfish.StarfishGame;
import com.badlogic.drop.ch06.rhythmtapper.RecorderGame;
import com.badlogic.drop.ch06.rhythmtapper.RhythmGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new RhythmGame(), config);
	}
}
