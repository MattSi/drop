package com.badlogic.drop.ch08;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Paddle extends BaseActor{
    public Paddle(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("bricks/paddle.png");
    }
}
