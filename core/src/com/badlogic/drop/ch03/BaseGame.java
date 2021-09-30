package com.badlogic.drop.ch03;

import com.badlogic.gdx.Game;

public class BaseGame extends Game {
    private static BaseGame game;

    public BaseGame(){
        game = this;
    }

    public static void setActiveScreen(BaseScreen s){
        game.setScreen(s);
    }

    @Override
    public void create() {

    }
}
