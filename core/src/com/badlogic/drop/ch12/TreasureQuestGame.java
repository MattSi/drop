package com.badlogic.drop.ch12;

public class TreasureQuestGame extends BaseGame
{
    public void create() 
    {        
        super.create();
        setActiveScreen( new LevelScreen() );
    }
}