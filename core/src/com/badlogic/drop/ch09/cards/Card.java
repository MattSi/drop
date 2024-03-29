package com.badlogic.drop.ch09.cards;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Card extends DragAndDropActor{
    public static String[] rankNames = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    public static String[] suitNames = {"Clubs", "Hearts","Spades","Diamonds"};

    private int rankValue;
    private int suitValue;

    public int getRankValue() {
        return rankValue;
    }

    public void setRankValue(int rankValue) {
        this.rankValue = rankValue;
    }

    public String getRankName(){
        return rankNames[getRankValue()];
    }

    public int getSuitValue() {
        return suitValue;
    }

    public void setSuitValue(int suitValue) {
        this.suitValue = suitValue;
    }

    public String getSuitName(){
        return suitNames[getSuitValue()];
    }

    public Card(float x, float y, Stage s) {
        super(x, y, s);
    }


    public void onDrop() {
        if(hasDropTarget()){
            Pile pile = (Pile) getDropTarget();
            Card topCard = pile.getTopCard();

            if(  this.getRankValue() == topCard.getRankValue() + 1
              && this.getSuitValue() == topCard.getSuitValue()){
                moveToActor(pile);
                pile.addCard(this);
                this.setDraggable(false);
            } else {
                // avoid block view of pile when incorrect
                moveToStart();
            }
        }

    }

    public void setRankSuitValues(int r, int s){
        setRankValue(r);
        setSuitValue(s);

        String imageFileName = "cards/Card"
                + getSuitName() + getRankName() + ".png";
        loadTexture(imageFileName);
        setSize(80, 100);
        setBoundaryRectangle();
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        boundToWorld();
    }

    @Override
    public String toString() {
        return "Card{" +
                "rankValue=" + rankValue +
                ", suitValue=" + suitValue +
                '}';
    }
}
