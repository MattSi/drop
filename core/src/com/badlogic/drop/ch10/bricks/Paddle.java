package com.badlogic.drop.ch10.bricks;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Paddle extends BaseActor {
    public Paddle(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("bricks/paddle.png");
    }
}
