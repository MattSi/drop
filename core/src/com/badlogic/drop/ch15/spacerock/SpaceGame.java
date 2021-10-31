package com.badlogic.drop.ch15.spacerock;


public class SpaceGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
