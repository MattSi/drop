package com.badlogic.drop.ch06.rhythmtapper;

public class RecorderGame extends BaseGame{

    @Override
    public void create() {
        super.create();
        setActiveScreen(new RecordScreen());
    }
}
