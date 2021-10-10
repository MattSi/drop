package com.badlogic.drop.ch08;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Brick extends BaseActor{
    public Brick(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("bricks/brick-gray.png");
    }
}
