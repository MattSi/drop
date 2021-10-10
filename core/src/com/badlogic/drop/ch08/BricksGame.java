package com.badlogic.drop.ch08;

public class BricksGame extends BaseGame{
    @Override
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
