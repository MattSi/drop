package com.badlogic.drop.ch09.cards;


public class PickupGame extends BaseGame{
    @Override
    public void create() {
        super.create();
        setActiveScreen( new LevelScreen() );
    }
}
