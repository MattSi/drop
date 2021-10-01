package com.badlogic.drop.desktop;

import com.badlogic.drop.ch04.SpaceGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop";
		config.width = 800;
		config.height = 600;
		//new LwjglApplication(new Drop(), config);
		new LwjglApplication(new SpaceGame(), config);
	}
}
