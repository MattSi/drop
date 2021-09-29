package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Drop extends Game {

	SpriteBatch batch;
	BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();

		// Use LibGDX's default Arial font
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}




	@Override
	public void render () {
		super.render(); // important


	}
	
	@Override
	public void dispose () {



		batch.dispose();
		font.dispose();
	}
}
