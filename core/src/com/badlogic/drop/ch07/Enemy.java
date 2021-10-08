package com.badlogic.drop.ch07;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Enemy extends BaseActor{
    public Enemy(float x, float y, Stage s) {
        super(x, y, s);

        String[] filenames = {
                "planedodger/planeRed0.png",
                "planedodger/planeRed1.png",
                "planedodger/planeRed2.png",
                "planedodger/planeRed1.png"
        };

        loadAnimationFromFiles(filenames, 0.1f, true);

        setSpeed(100);
        setMotionAngle(180);
        setBoundaryPolygon(8);

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
    }
}
