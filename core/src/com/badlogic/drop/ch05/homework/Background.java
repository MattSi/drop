package com.badlogic.drop.ch05.homework;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Background extends BaseActor{
    public Animation hallway;
    public Animation classroom;
    public Animation scienceLab;
    public Animation library;

    public Background(float x, float y, Stage s) {
        super(x, y, s);
        hallway = loadTexture("homework/bg-hallway.jpg");
        classroom = loadTexture("homework/bg-classroom.jpg");
        scienceLab = loadTexture("homework/bg-science-lab.jpg");
        library = loadTexture("homework/bg-library.jpg");
        setSize(800, 600);
    }
}
