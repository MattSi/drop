package com.badlogic.drop.desktop;


import com.badlogic.drop.ch11.JumpingJackGame;
import com.badlogic.drop.ch12.TreasureQuestGame;
import com.badlogic.drop.ch14.MazeGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new MazeGame(), config);
	}
}
