package com.badlogic.drop.ch07;

public class PlaneDodgerGame extends BaseGame{
    @Override
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
