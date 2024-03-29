package com.badlogic.drop.ch01;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HelloWorldImage extends Game {
    /*
    A Texture is an object that stores image-related data: the dimensions(width and height)
    of an image and the color of each pixel.

    A SpriteBatch is an object that draws images to the screen.

     */
    private Texture texture;
    private SpriteBatch batch;

    @Override
    public void create() {
        FileHandle worldFile = Gdx.files.internal("world.png");
        texture = new Texture(worldFile);
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 192,112);
        batch.end();
    }
}
