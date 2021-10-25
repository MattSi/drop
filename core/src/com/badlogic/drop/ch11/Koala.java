package com.badlogic.drop.ch11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Koala extends BaseActor{
    private Animation stand;
    private Animation walk;
    private Animation jump;

    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;

    private float jumpSpeed;
    private BaseActor belowSensor;

    public Koala(float x, float y, Stage s) {
        super(x, y, s);

        stand = loadTexture("jumpingjack/koala/stand.png");
        jump = loadTexture("jumpingjack/koala/jump.png");

        String[] walkFileNames = {
                "jumpingjack/koala/walk-1.png",
                "jumpingjack/koala/walk-2.png",
                "jumpingjack/koala/walk-3.png",
                "jumpingjack/koala/walk-2.png"
        };

        walk = loadAnimationFromFiles(walkFileNames, 0.2f, true);

        maxHorizontalSpeed = 100;
        walkAcceleration = 200;
        walkDeceleration = 200;
        gravity = 700;
        maxVerticalSpeed = 1000;

        jumpSpeed = 450;
        setBoundaryPolygon(6);
        belowSensor = new BaseActor(0,0,s);
        belowSensor.loadTexture("jumpingjack/white.png");
        belowSensor.setSize(this.getWidth()-8, 8);
        belowSensor.setBoundaryRectangle();
        belowSensor.setVisible(false);
    }

    @Override
    public void act(float dt) {
        super.act(dt);


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            accelerationVec.add(-walkAcceleration, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            accelerationVec.add(walkAcceleration, 0);
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT)  && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            float decelerationAmount = walkDeceleration * dt;
            float walkDirection;

            if(velocityVec.x > 0)
                walkDirection = 1;
            else
                walkDirection = -1;

            float walkSpeed = Math.abs(velocityVec.x);
            walkSpeed -= decelerationAmount;
            if(walkSpeed < 0)
                walkSpeed = 0;
            velocityVec.x = walkSpeed * walkDirection;
        }

        // apply gravity
        accelerationVec.add(0, -gravity);
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed);
        velocityVec.y = MathUtils.clamp(velocityVec.y, -maxVerticalSpeed, maxVerticalSpeed);

        moveBy(velocityVec.x * dt, velocityVec.y * dt);
        accelerationVec.set(0,0);

        // move the below sensor below the koala
        belowSensor.setPosition( getX() + 4, getY() - 8 );

        if(this.isOnSolid()){
            belowSensor.setColor(Color.GREEN);
            if ( velocityVec.x == 0 )
                setAnimation(stand);
            else
                setAnimation(walk);
        } else {
            belowSensor.setColor(Color.RED);
            setAnimation(jump);
        }


        if ( velocityVec.x > 0 ) // face right
            setScaleX(1);

        if ( velocityVec.x < 0 ) // face left
            setScaleX(-1);

        alignCamera();
        boundToWorld();
    }

    public boolean belowOverlaps(BaseActor actor){
        return belowSensor.overlaps(actor);
    }

    public boolean isOnSolid(){
        for(BaseActor actor : BaseActor.getList(getStage(), "com.badlogic.drop.ch11.Solid")){
            Solid solid = (Solid) actor;
            if(belowOverlaps(solid) && solid.isEnabled()){
                return true;
            }
        }

        return false;
    }

    public boolean isFalling(){
        return (velocityVec.y < 0);
    }

    public void spring(){
        velocityVec.y = 1.5f * jumpSpeed;
    }

    public void jump(){
        velocityVec.y = jumpSpeed;
    }

    public boolean isJumping(){
        return (velocityVec.y > 0);
    }
}
