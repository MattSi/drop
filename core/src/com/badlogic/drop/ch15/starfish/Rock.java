package com.badlogic.drop.ch15.starfish;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Rock extends BaseActor {
    public Rock(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("starfish/rock.png");
        setBoundaryPolygon(8);
    }
}
