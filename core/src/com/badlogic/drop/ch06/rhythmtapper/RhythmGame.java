package com.badlogic.drop.ch06.rhythmtapper;

public class RhythmGame extends BaseGame{
    @Override
    public void create() {
        super.create();
        setActiveScreen(new RhythmScreen());
    }
}
