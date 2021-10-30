package com.badlogic.drop.ch14;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Wall extends BaseActor{
    public Wall(float x, float y, float w, float h, Stage s) {
        super(x, y, s);
        loadTexture("maze/square.jpg");
        setSize(w, h);
        setBoundaryRectangle();
    }
}
