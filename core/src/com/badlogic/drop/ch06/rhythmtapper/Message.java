package com.badlogic.drop.ch06.rhythmtapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Message extends BaseActor{
    public Animation perfect;
    public Animation great;
    public Animation good;
    public Animation almost;
    public Animation miss;


    private Animation countdown3;
    private Animation countdown2;
    private Animation countdown1;
    private Animation countdownGo;
    private Animation congratulations;

    private Sound blip;
    private Sound tone;

    public Message(float x, float y, Stage s) {
        super(x, y, s);

        perfect = loadTexture("rhythmtapper/perfect.png");
        great = loadTexture("rhythmtapper/great.png");
        good = loadTexture("rhythmtapper/good.png");
        almost = loadTexture("rhythmtapper/almost.png");
        miss = loadTexture("rhythmtapper/miss.png");

        countdown3 = loadTexture("rhythmtapper/countdown-3.png");
        countdown2 = loadTexture("rhythmtapper/countdown-2.png");
        countdown1 = loadTexture("rhythmtapper/countdown-1.png");
        countdownGo = loadTexture("rhythmtapper/countdown-go.png");

        congratulations = loadTexture("rhythmtapper/congratulations.png");

        blip = Gdx.audio.newSound(Gdx.files.internal("rhythmtapper/blip.wav"));
        tone = Gdx.audio.newSound(Gdx.files.internal("rhythmtapper/tone.wav"));

    }

    public void pulseFade(){
        setOpacity(1);
        clearActions();
        Action pulseFade = Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.05f),
                Actions.scaleTo(1.0f, 1.0f, 0.05f),
                Actions.delay(1),
                Actions.fadeOut(0.5f)
        );
        addAction(pulseFade);
    }

    public void displayCountdown(){
        Action countdown = Actions.sequence(
                Actions.run( () -> setAnimation(countdown3) ),
                Actions.run( () -> blip.play() ),
                Actions.alpha(1),
                Actions.scaleTo(1.1f,1.1f, 0.05f), Actions.scaleTo(1.0f,1.0f, 0.05f),
                Actions.delay(0.5f), Actions.fadeOut(0.4f),
                Actions.run( () -> setAnimation(countdown2) ),
                Actions.run( () -> blip.play() ),
                Actions.alpha(1),
                Actions.scaleTo(1.1f,1.1f, 0.05f), Actions.scaleTo(1.0f,1.0f, 0.05f),
                Actions.delay(0.5f), Actions.fadeOut(0.4f),
                Actions.run( () -> setAnimation(countdown1) ),
                Actions.run( () -> blip.play() ),
                Actions.alpha(1),
                Actions.scaleTo(1.1f,1.1f, 0.05f), Actions.scaleTo(1.0f,1.0f, 0.05f),
                Actions.delay(0.5f), Actions.fadeOut(0.4f),
                Actions.run( () -> setAnimation(countdownGo) ),
                Actions.run( () -> tone.play() ),
                Actions.alpha(1),
                Actions.fadeOut(1)
        );

        addAction(countdown);
    }

    public void displayCongratulations(){
        setOpacity(0);
        setAnimation(congratulations);
        setScale(2);
        addAction(Actions.fadeIn(4));

    }
}
