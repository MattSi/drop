package com.badlogic.drop.ch11;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Springboard extends BaseActor{
    public Springboard(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("jumpingjack/items/springboard.png", 1,3,0.2f, true);
    }
}
