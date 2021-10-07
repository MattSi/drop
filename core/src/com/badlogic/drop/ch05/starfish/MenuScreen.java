package com.badlogic.drop.ch05.starfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends BaseScreen {
    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0,0,mainStage);
        ocean.loadTexture("starfish/water.jpg");
        ocean.setSize(800,600);

        BaseActor title = new BaseActor(0,0,mainStage);
        title.loadTexture("starfish/starfish-collector.png");

        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
        //startButton.setPosition(150,150);
        uiStage.addActor(startButton);

        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        //quitButton.setPosition(500,150);
        uiStage.addActor(quitButton);

        uiTable.add(title).colspan(2);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton);

        startButton.addListener((Event e) ->{
           if (!(e instanceof InputEvent) ||
            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
            return false;
           StarfishGame.setActiveScreen(new StoryScreen());
           return false;
        });

        quitButton.addListener(
                (Event e) -> {
                    if (!(e instanceof InputEvent) ||
                            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                        return false;
                    Gdx.app.exit();
                    return false;
                });
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
            StarfishGame.setActiveScreen(new StoryScreen());
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }
}
