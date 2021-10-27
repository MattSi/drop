package com.badlogic.drop.ch12;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Bush extends Solid{
    public Bush(float x, float y,  Stage s) {
        super(x, y, 32, 32, s);
        loadTexture("treasurequest/bush.png");
        setBoundaryPolygon(8);
    }
}
