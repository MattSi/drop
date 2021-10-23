package com.badlogic.drop.ch10.bricks;

public class BricksGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
