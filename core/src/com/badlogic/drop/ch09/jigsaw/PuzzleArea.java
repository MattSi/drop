package com.badlogic.drop.ch09.jigsaw;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class PuzzleArea extends DropTargetActor{
    private int row;
    private int col;

    public PuzzleArea(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("jigsaw/border.jpg");
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
