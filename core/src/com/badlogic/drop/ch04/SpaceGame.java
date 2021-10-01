package com.badlogic.drop.ch04;


public class SpaceGame extends BaseGame{
    @Override
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
