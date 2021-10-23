package com.badlogic.drop.ch09.cards;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DragAndDropActor extends BaseActor {
    private DragAndDropActor self;

    private float grabOffsetX;
    private float grabOffsetY;
    private boolean draggable;

    private float startPositionX;
    private float startPositionY;

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public DropTargetActor getDropTarget() {
        return dropTarget;
    }

    public void setDropTarget(DropTargetActor a) {
        dropTarget = a;
    }

    public boolean hasDropTarget(){
        return (dropTarget != null);
    }

    private DropTargetActor dropTarget;

    public DragAndDropActor(float x, float y, Stage s) {
        super(x, y, s);

        self = this;
        draggable = true;

        addListener(
                new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(!self.isDraggable()){
                            return false;
                        }
                        self.grabOffsetX = x;
                        self.grabOffsetY = y;
                        self.startPositionX = self.getX();
                        self.startPositionY = self.getY();
                        self.toFront();

                        self.addAction(Actions.scaleTo(1.1f, 1.1f, 0.25f));
                        self.onDragStart();
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        self.setDropTarget(null);

                        // keep track of distance to closest object
                        float closestDistance = Float.MAX_VALUE;

                        for(BaseActor actor: BaseActor.getList(self.getStage(), "com.badlogic.drop.ch09.cards.DropTargetActor")){
                            DropTargetActor target = (DropTargetActor) actor;
                            if (target.isTargetable() && self.overlaps(target)){
                                float currentDistance = Vector2.dst(self.getX(),self.getY(), target.getX(), target.getY());

                                // check if this target is even closer
                                if(currentDistance < closestDistance){
                                    self.setDropTarget(target);
                                    closestDistance = currentDistance;
                                }
                            }
                        }

                        self.addAction(Actions.scaleTo(1.0f, 1.0f, 0.25f));
                        self.onDrop();

                    }

                    @Override
                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        // will add code later
                        float deltaX = x - self.grabOffsetX;
                        float deltaY = y - self.grabOffsetY;

                        self.moveBy(deltaX, deltaY);
                    }
                }
        );
    }

    public void moveToStart(){
        addAction(Actions.moveTo(startPositionX, startPositionY, 0.5f, Interpolation.pow3));
    }
    public void moveToActor(BaseActor other){
        float x = other.getX() + (other.getWidth() - this.getWidth()) / 2;
        float y = other.getY() + (other.getHeight() - this.getHeight()) / 2;

        addAction(Actions.moveTo(x,y,0.5f, Interpolation.pow3));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
    }

    protected void onDragStart(){

    }

    public void onDrop(){

    }
}
