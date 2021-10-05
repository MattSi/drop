package com.badlogic.drop.ch05.homework;

public class HomeworkGame extends BaseGame{
    @Override
    public void create() {
        super.create();
        setActiveScreen(new MenuScreen());
    }
}
