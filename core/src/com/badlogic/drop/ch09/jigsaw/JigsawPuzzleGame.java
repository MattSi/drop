package com.badlogic.drop.ch09.jigsaw;

public class JigsawPuzzleGame extends BaseGame
{
    public void create() 
    {        
        super.create();
        setActiveScreen( new LevelScreen() );
    }
}