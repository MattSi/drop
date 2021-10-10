package com.badlogic.drop.ch07;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LevelScreen extends BaseScreen{
    Plane plane;
    float startTimer;
    float starSpawnInterval;
    int score;
    Label scoreLabel;

    float enemyTimer;
    float enemySpawnInterval;
    float enemySpeed;
    boolean gameOver;
    BaseActor gameOverMessage;

    Music backgroundMusic;
    Sound sparkleSound;
    Sound explosionSound;

    @Override
    public void initialize() {
        new Sky(0,0,mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0,0, mainStage);
        new Ground(800, 0, mainStage);

        plane = new Plane(100, 500, mainStage);
        BaseActor.setWorldBounds(800, 600);

        startTimer = 0;
        starSpawnInterval = 4;
        score = 0;
        scoreLabel = new Label(Integer.toString(score), BaseGame.labelStyle);
        uiTable.pad(10);
        uiTable.add(scoreLabel);
        uiTable.row();
        uiTable.add().expandY();

        enemyTimer = 0;
        enemySpawnInterval = 1.5f;
        enemySpeed = 100;

        gameOver = false;
        gameOverMessage = new BaseActor(0,0, uiStage);
        gameOverMessage.loadTexture("planedodger/game-over.png");
        gameOverMessage.setVisible(false);

        uiTable.add(gameOverMessage).expandY();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("planedodger/Prelude-and-Action.mp3"));
        sparkleSound = Gdx.audio.newSound(Gdx.files.internal("planedodger/sparkle.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("planedodger/explosion.wav"));


        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.00f);
        backgroundMusic.play();
    }

    @Override
    public void update(float dt) {
        if(gameOver){
            return;
        }
        startTimer += dt;

        if(startTimer > starSpawnInterval){
            new Star(800, MathUtils.random(100, 500), mainStage);
            startTimer = 0;
        }

        for(BaseActor star : BaseActor.getList(mainStage, "com.badlogic.drop.ch07.Star")){
            if(plane.overlaps(star)){
                Sparkle sp = new Sparkle(0,0, mainStage);
                sp.centerAtActor(star);
                sparkleSound.play();
                star.remove();
                score ++;
                scoreLabel.setText(Integer.toString(score));
            }
        }

        enemyTimer += dt;
        if(enemyTimer > enemySpawnInterval){
            Enemy enemy = new Enemy(800, MathUtils.random(100, 500), mainStage);
            enemy.setSpeed(enemySpeed);

            enemyTimer = 0;
            enemySpawnInterval -= 0.1f;
            enemySpeed += 10;

            if(enemySpawnInterval < 0.5f){
                enemySpawnInterval = 0.5f;
            }
            if(enemySpeed > 400){
                enemySpeed = 400;
            }
        }

        for(BaseActor enemy : BaseActor.getList(mainStage, "com.badlogic.drop.ch07.Enemy")){
            if(plane.overlaps(enemy)){
                Explosion ex = new Explosion(0,0,mainStage);
                ex.centerAtActor(plane);
                ex.setScale(3);
                explosionSound.play();
                backgroundMusic.stop();

                plane.remove();
                gameOver = true;
                gameOverMessage.setVisible(true);
            }

            if(enemy.getX() + enemy.getWidth() < 0){
                score ++;
                scoreLabel.setText(Integer.toString(score));
                enemy.remove();
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE){
            plane.boost();
        }

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
