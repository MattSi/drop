package com.badlogic.drop.ch10.starfish;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameBeta extends Game {
    protected Stage mainStage;
    protected Stage uiStage;

    public abstract void initialize();
    public abstract void update(float dt);
    @Override
    public void create() {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }

    @Override
    public void render() {
//        super.render();
        float dt = Gdx.graphics.getDeltaTime();
        mainStage.act(dt);
        uiStage.act(dt);
        update(dt);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
        uiStage.draw();


    }
}
