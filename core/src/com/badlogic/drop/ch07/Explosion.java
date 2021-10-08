package com.badlogic.drop.ch07;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Explosion extends BaseActor
{
    public Explosion(float x, float y, Stage s)
    {
       super(x,y,s);
       loadAnimationFromSheet("planedodger/explosion.png", 6,6, 0.02f, false);
    }
    
    public void act(float dt)
    {
        super.act(dt);
        
        if ( isAnimationFinished() )
            remove();
    }
}