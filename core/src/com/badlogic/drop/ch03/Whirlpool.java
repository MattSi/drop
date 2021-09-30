package com.badlogic.drop.ch03;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Whirlpool extends BaseActor{
    public Whirlpool(float x, float y, Stage s) {
        super(x, y, s);

        loadAnimationFromSheet("whirlpool.png",2, 5, 0.1f, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(isAnimationFinished()){
            remove();
        }
    }
}
