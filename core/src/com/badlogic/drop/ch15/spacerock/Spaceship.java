package com.badlogic.drop.ch15.spacerock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Spaceship extends BaseActor {
    private Thrusters thrusters;
    private ThrusterEffect thrusterEffect;
    private Shield shield;
    public int shieldPower;
    public Spaceship(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("spacerock/spaceship.png");
        setBoundaryPolygon(8);

        setAcceleration(200);
        setMaxSpeed(100);
        setDeceleration(10);

        thrusters = new Thrusters(0,0,s);
        addActor(thrusters);
        thrusters.setPosition(-thrusters.getWidth(),
                getHeight()/2 - thrusters.getHeight()/2);

        shield = new Shield(0,0,s);
        addActor(shield);
        shield.centerAtPosition(getWidth()/2, getHeight()/2);
        shieldPower=100;

        thrusterEffect = new ThrusterEffect();
        thrusterEffect.setPosition(0, 32);
        thrusterEffect.setRotation(90);
        thrusterEffect.setScale(0.25f);
        addActor(thrusterEffect);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        float degreesPerSecond = 120; // rotation speed
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rotateBy(degreesPerSecond * delta);

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rotateBy((degreesPerSecond * delta * -1));

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerationAtAngle(getRotation());
            //thrusters.setVisible(true);
            thrusterEffect.start();
        } else {
            //thrusters.setVisible(false);
            thrusterEffect.stop();
        }
        shield.setOpacity(shieldPower/100f);
        if(shieldPower <=0)
            shield.setVisible(false);

        applyPhysics(delta);
        wrapAroundWorld();
    }

    public void warp(){
        if(getStage() == null)
            return;

        Warp warp1 = new Warp(0,0,getStage());
        warp1.centerAtActor(this);
        setPosition(MathUtils.random(800), MathUtils.random(600));
        Warp warp2 = new Warp(0,0,getStage());
        warp2.centerAtActor(this);
    }

    public void shoot(){
        if(getStage() ==null)
            return;

        Laser laser = new Laser(0,0,getStage());
        laser.centerAtActor(this);
        laser.setRotation(this.getRotation());
        laser.setMotionAngle(getRotation());
    }
}
