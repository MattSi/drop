package com.badlogic.drop.ch10.starfish;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Whirlpool extends BaseActor {
    public Whirlpool(float x, float y, Stage s) {
        super(x, y, s);

        loadAnimationFromSheet("starfish/whirlpool.png",2, 5, 0.1f, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(isAnimationFinished()){
            remove();
        }
    }
}
