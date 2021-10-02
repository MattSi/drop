package com.badlogic.drop.ch05;

public class StarfishGame extends BaseGame {


    @Override
    public void create(){
        super.create();
        setActiveScreen(new MenuScreen());
    }
}
