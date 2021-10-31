package com.badlogic.drop.ch15.starfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor {
    String vertexShaderCode;
    String fragmentShaderCode;
    ShaderProgram shaderProgram;
    float time;

    public Turtle(float x, float y, Stage s) {
        super(x, y, s);

        String[] filenames = {
                "starfish/turtle-1.png","starfish/turtle-2.png",
                "starfish/turtle-3.png","starfish/turtle-4.png",
                "starfish/turtle-5.png","starfish/turtle-6.png"};

        loadAnimationFromFiles(filenames, 0.1f, true);

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);

        time = 0;
        vertexShaderCode = Gdx.files.internal("starfish/shaders/default.vs").readString();
        fragmentShaderCode = Gdx.files.internal("starfish/shaders/wave.fs").readString();
        shaderProgram = new ShaderProgram(vertexShaderCode, fragmentShaderCode);
        if(!shaderProgram.isCompiled()){
            System.out.println("Shader compile error: " + shaderProgram.getLog());
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        time += delta;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            accelerationAtAngle(180);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            accelerationAtAngle(0);

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            accelerationAtAngle(90);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            accelerationAtAngle(270);

        applyPhysics(delta);

        setAnimationPaused(!isMoving());

        if(getSpeed() > 0){
            setRotation(getMotionAngle());
        }

        boundToWorld();
        alignCamera();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(shaderProgram);
        shaderProgram.setUniformf("u_time", time);
        shaderProgram.setUniformf("u_imageSize", new Vector2(getWidth(), getHeight()));
        shaderProgram.setUniformf("u_amplitude", new Vector2(2,3));
        shaderProgram.setUniformf("u_wavelength", new Vector2(17,19));
        shaderProgram.setUniformf("u_velocity", new Vector2(10,11));
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }
}
