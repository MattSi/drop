package com.badlogic.drop.ch02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Turtle extends ActorBeta{
    public Turtle() {
        super();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveBy(-1, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveBy(1, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            moveBy(0, 1);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveBy(0, -1);
    }
}
