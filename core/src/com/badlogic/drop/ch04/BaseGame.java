package com.badlogic.drop.ch04;

import com.badlogic.drop.ch03.BaseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class BaseGame extends Game {
    private static BaseGame game;

    public BaseGame(){
        game = this;
    }

    public static void setActiveScreen(LevelScreen s){
        game.setScreen(s);
    }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
    }
}
