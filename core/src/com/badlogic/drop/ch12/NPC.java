package com.badlogic.drop.ch12;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class NPC extends BaseActor{
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    public boolean isViewing() {
        return viewing;
    }

    public void setViewing(boolean viewing) {
        this.viewing = viewing;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
        if(ID.equals("Gatekeeper")){
            loadTexture("treasurequest/npc-1.png");
        } else if(ID.equals("Shopkeeper")){
            loadTexture("treasurequest/npc-2.png");
        } else {
            loadTexture("treasurequest/npc-3.png");
        }
    }

    private boolean viewing;

    private String ID;

    public NPC(float x, float y, Stage s) {
        super(x, y, s);
        text = "";
        viewing = false;
    }
}
