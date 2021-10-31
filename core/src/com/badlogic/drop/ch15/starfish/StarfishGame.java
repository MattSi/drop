package com.badlogic.drop.ch15.starfish;

public class StarfishGame extends BaseGame {


    @Override
    public void create(){
        super.create();
        setActiveScreen(new MenuScreen());
    }
}
