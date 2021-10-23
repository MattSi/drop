package com.badlogic.drop.desktop;

import com.badlogic.drop.ch07.PlaneDodgerGame;
import com.badlogic.drop.ch09.cards.PickupGame;
import com.badlogic.drop.ch09.jigsaw.JigsawPuzzleGame;
import com.badlogic.drop.ch10.bricks.BricksGame;
import com.badlogic.drop.ch10.starfish.StarfishGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new BricksGame(), config);
	}
}
