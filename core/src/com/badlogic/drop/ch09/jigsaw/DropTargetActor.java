package com.badlogic.drop.ch09.jigsaw;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class DropTargetActor extends BaseActor{
    private boolean targetable;

    public DropTargetActor(float x, float y, Stage s) {
        super(x, y, s);
        targetable = true;
    }

    public void setTargetable(boolean t){
        targetable = t;
    }

    public boolean isTargetable(){
        return targetable;
    }
}
