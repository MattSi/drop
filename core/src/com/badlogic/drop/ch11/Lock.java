package com.badlogic.drop.ch11;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Lock extends Solid{
    public Lock(float x, float y, Stage s) {
        super(x, y, 32, 32, s);
        loadTexture("jumpingjack/items/lock.png");
    }
}
