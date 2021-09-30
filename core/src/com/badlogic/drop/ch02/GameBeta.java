package com.badlogic.drop.ch02;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameBeta extends Game {
    protected Stage mainStage;

    public abstract void initialize();

    public abstract void update(float dt);
}
