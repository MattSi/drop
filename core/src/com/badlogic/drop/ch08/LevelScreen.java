package com.badlogic.drop.ch08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LevelScreen extends BaseScreen{
    Paddle paddle;
    Ball ball;

    int score;
    int balls;
    Label scoreLabel;
    Label ballsLabel;
    Label messageLabel;
    Sound bounceSound;
    Sound brickBumpSound;
    Sound wallBumpSound;
    Sound itemAppearSound;
    Sound itemCollectSound;
    Music backgroundMusic;

    @Override
    public void initialize() {

        bounceSound = Gdx.audio.newSound(Gdx.files.internal("bricks/boing.wav"));
        brickBumpSound = Gdx.audio.newSound(Gdx.files.internal("bricks/bump.wav"));
        wallBumpSound = Gdx.audio.newSound(Gdx.files.internal("bricks/bump-low.wav"));
        itemAppearSound = Gdx.audio.newSound(Gdx.files.internal("bricks/swoosh.wav"));
        itemCollectSound = Gdx.audio.newSound(Gdx.files.internal("bricks/pop.wav"));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bricks/rollin-at-5.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture("bricks/space.png");
        BaseActor.setWorldBounds(background);
        paddle = new Paddle(320, 32, mainStage);

        new Wall(0,0,20, 600, mainStage); //left wall
        new Wall(780,0,20, 600, mainStage); //right wall
        new Wall(0,550,800, 50, mainStage); //left wall

        Brick tempBrick = new Brick(0,0, mainStage);
        float brickWidth = tempBrick.getWidth();
        float brickHeight = tempBrick.getHeight();
        tempBrick.remove();

        int totalRows = 10;
        int totalCols = 10;
        float marginX = (800 - totalCols * brickWidth) / 2;
        float marginY = (600 - totalRows * brickHeight) - 120;

        for (int rowNum = 0; rowNum<totalRows; rowNum++){
            for(int colNUm = 0; colNUm<totalCols; colNUm++){
                float x = marginX + brickWidth * colNUm;
                float y = marginY + brickHeight * rowNum;

                new Brick(x, y, mainStage);
            }
        }

        ball = new Ball(0,0,mainStage);

        score = 0;
        balls = 3;
        scoreLabel = new Label("Score: " + score, BaseGame.labelStyle);
        ballsLabel = new Label("Balls: " + balls, BaseGame.labelStyle);
        messageLabel = new Label("Click to start", BaseGame.labelStyle);
        messageLabel.setColor(Color.CYAN);

        uiTable.pad(5);
        uiTable.add(scoreLabel);
        uiTable.add().expandX();
        uiTable.add(ballsLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();


    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(ball.isPaused()){
            ball.setPaused(false);
            messageLabel.setVisible(false);
        }
        return false;
    }

    @Override
    public void update(float dt) {

        float mouseX = Gdx.input.getX();
        paddle.setX(mouseX - paddle.getWidth()/2);
        paddle.boundToWorld();

        if(ball.isPaused()){
            ball.setX(paddle.getX() + paddle.getWidth()/2 - ball.getWidth()/2);
            ball.setY(paddle.getY() + paddle.getHeight()/2 + ball.getHeight()/2);
        }

        for(BaseActor wall : BaseActor.getList(mainStage, "com.badlogic.drop.ch08.Wall")){
            if(ball.overlaps(wall)){
                ball.bounceOff(wall);
                wallBumpSound.play();
            }
        }

        for(BaseActor brick : BaseActor.getList(mainStage, "com.badlogic.drop.ch08.Brick")){
            if(ball.overlaps(brick)){
                ball.bounceOff(brick);
                brick.remove();
                score += 100;
                scoreLabel.setText("Score: " + score);

                float spawnProbability = 20;
                if ( MathUtils.random(0, 100) < spawnProbability )
                {
                    Item i = new Item(0,0,mainStage);
                    i.centerAtActor(brick);
                    itemAppearSound.play();
                }

                brickBumpSound.play();
            }
        }

        if(ball.overlaps(paddle)){
            float ballCenterX = ball.getX() + ball.getWidth() / 2;
            float paddlePercentHit = (ballCenterX - paddle.getX()) / paddle.getWidth();
            float bounceAngle = MathUtils.lerpAngleDeg(150, 30, paddlePercentHit);
            ball.setMotionAngle(bounceAngle);
            bounceSound.play();
        }

        if(BaseActor.count(mainStage, "com.badlogic.drop.ch08.Brick") == 0){
            messageLabel.setText("You win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setVisible(true);
        }

        if(ball.getY() < -50 && BaseActor.count(mainStage, "com.badlogic.drop.ch08.Brick") >0){
            ball.remove();

            if (balls > 0){
                balls -= 1;
                ballsLabel.setText("Balls: " + balls);
                ball = new Ball(0,0, mainStage);

                messageLabel.setText("Click to start");
                messageLabel.setColor(Color.CYAN);
                messageLabel.setVisible(true);
            } else {
                messageLabel.setText("Game Over");
                messageLabel.setColor(Color.RED);
                messageLabel.setVisible(true);
            }
        }

        for(BaseActor item : BaseActor.getList(mainStage, "com.badlogic.drop.ch08.Item")){
            if(paddle.overlaps(item)){
                Item realItem = (Item) item;

                if(realItem.getType() == Item.Type.PADDLE_EXPAND){
                    paddle.setWidth(paddle.getWidth() * 1.25f);
                } else if(realItem.getType() == Item.Type.PADDLE_SHRINK){
                    paddle.setWidth(paddle.getWidth() * 0.80f);
                } else if(realItem.getType() == Item.Type.BALL_SPEED_UP){
                    ball.setSpeed(ball.getSpeed() * 1.50f);
                } else if(realItem.getType() == Item.Type.BALL_SPEED_DOWN){
                    ball.setSpeed(ball.getSpeed() * 0.90f);
                }
                paddle.setBoundaryRectangle();
                item.remove();
                itemCollectSound.play();
            }
        }
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
