package com.badlogic.drop.ch05.homework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * Extends functionality of the LibGDX Actor class.
 * by adding support for textures/animation, 
 * collision polygons, movement, world boundaries, and camera scrolling. 
 * Most game objects should extend this class; lists of extensions can be retrieved by stage and class name.
 * @see #Actor
 * @author Lee Stemkoski
 */
public class BaseActor extends Group {
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;
    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;

    public BaseActor(float x, float y, Stage s) {
        super();

        setPosition(x, y);
        s.addActor(this);
        animation = null;
        elapsedTime = 0f;
        animationPaused = false;
        velocityVec = new Vector2(0,0);
        accelerationVec = new Vector2(0,0);
        acceleration = 0f;
        maxSpeed = 400f;
        deceleration = 0f;
    }


    /** 
     *  If this object moves completely past the world bounds,
     *  adjust its position to the opposite side of the world.
     */
	public void wrapAroundWorld(){
        if(getX() + getWidth() < 0)
            setX(worldBounds.width);
        if(getX() > worldBounds.width)
            setX(-getWidth());

        if(getY() + getHeight() < 0)
            setY(worldBounds.height);
        if(getY() > worldBounds.height)
            setY(-getHeight());
	}
	
	
	/**
     *  Align center of actor at given position coordinates.
     *  @param x x-coordinate to center at
     *  @param y y-coordinate to center at
     */
	 public void centerAtPosition(float x, float y){
        setPosition(x - getWidth()/2, y - getHeight()/2);
    }

    public void centerAtActor(BaseActor other){
        centerAtPosition(other.getX() + other.getWidth()/2,
                other.getY() + other.getHeight()/2);
    }
    public void setAnimation(Animation<TextureRegion> anim){
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2, h/2);

        if(boundaryPolygon == null){
            setBoundaryRectangle();
        }
    }

    public void setAnimationPaused(boolean pause){
        animationPaused = pause;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!animationPaused)
            elapsedTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if(animation!=null && isVisible())
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        super.draw(batch, parentAlpha);
    }


    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop){
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<>();

        for( int i=0; i<fileCount; i++){
            String fileName = fileNames[i];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }
        if(animation == null){
            setAnimation(anim);
        }
        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop){
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth()/cols;
        int frameHeight = texture.getHeight()/rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<>();

        for(int r = 0; r<rows; r++) {
            for (int c = 0; c < cols; c++) {
                textureArray.add(temp[r][c]);
            }
        }
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }
        if(animation == null){
            setAnimation(anim);
        }
        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName){
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setSpeed(float speed){
        // if length is zero, then assume motion angle is zero degrees.
        if(velocityVec.len() == 0){
            velocityVec.set(speed, 0);
        } else {
            velocityVec.setLength(speed);
        }
    }
    public float getSpeed(){
        return velocityVec.len();
    }

    public void setMotionAngle(float angle){
        velocityVec.setAngleDeg(angle);
    }

    public float getMotionAngle(){
        return velocityVec.angleDeg();
    }

    public boolean isMoving(){
        return getSpeed()>0;
    }

    public void setAcceleration(float acc){
        acceleration = acc;
    }

    public void accelerationAtAngle(float angle){
        accelerationVec.add(new Vector2(acceleration,0).setAngleDeg(angle));
    }

    public void accelerateForward(){
        accelerationAtAngle(getRotation());
    }

    public void setMaxSpeed(float ms){
        maxSpeed = ms;
    }

    public void setDeceleration(float dec){
        deceleration = dec;
    }

    public void applyPhysics(float delta){
        velocityVec.add(accelerationVec.x * delta, accelerationVec.y * delta);
        float speed = getSpeed();

        if (accelerationVec.len() == 0) {
            speed -= deceleration * delta;
        }

        speed = MathUtils.clamp(speed, 0, maxSpeed);

        setSpeed(speed);
        moveBy(velocityVec.x * delta, velocityVec.y * delta);
        accelerationVec.set(0,0);
    }

    public void setBoundaryRectangle(){
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0,w,0,w,h,0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides){
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2 * numSides];
        for(int i=0; i<numSides; i++){
            float angleRad
                    = i * MathUtils.PI2 / numSides;
            // x-coordinate
            vertices[2*i] = w/2 * MathUtils.cos(angleRad) + w/2;
            // y-coordinate
            vertices[2*i +1] = h/2 * MathUtils.sin(angleRad) + h/2;
        }

        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon(){
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean overlaps(BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return false;

        return Intersector.overlapConvexPolygons(poly1, poly2);
    }

   

    public void setOpacity(float opacity){
        this.getColor().a = opacity;
    }

    public Vector2 preventOverlap(BaseActor other){
        Polygon poly1 = getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return null;

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
        if (!polygonOverlap)
            return null;
        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }


    public static ArrayList<BaseActor> getList(Stage stage, String className){
        ArrayList<BaseActor> list = new ArrayList<>();
        Class theClass = null;

        try{
            theClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(Actor a : stage.getActors()){
            if(theClass.isInstance(a)){
                list.add((BaseActor) a);
            }
        }
        return list;
    }

    public static int count(Stage stage, String className){
        return getList(stage, className).size();
    }

    public static void setWorldBounds(float width, float height){
        worldBounds = new Rectangle(0,0,width, height);
    }

    public static void setWorldBounds(BaseActor ba){
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    public void boundToWorld(){
        if(getX() < 0){
            setX(0);
        }
        if(getX() + getWidth() > worldBounds.width){
            setX(worldBounds.width - getWidth());
        }
        if(getY() <0 ){
            setY(0);
        }
        if(getY() + getHeight() > worldBounds.height){
            setY(worldBounds.height - getHeight());
        }
    }

    public void alignCamera(){
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

        //center camera on actor
        cam.position.set(this.getX()+this.getOriginX(), this.getY()+this.getOriginY(), 0);

        // bound camera to layout
        cam.position.x = MathUtils.clamp(cam.position.x,
                cam.viewportWidth/2, worldBounds.width - cam.viewportWidth/2);
        cam.position.y = MathUtils.clamp(cam.position.y,
                cam.viewportHeight/2,worldBounds.height - cam.viewportHeight/2);
        cam.update();
    }

    

    public boolean isWithinDistance(float distance, BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        float scaleX = (getWidth() + 2 * distance) / getWidth();
        float scaleY = (getHeight() + 2 * distance) / getHeight();
        poly1.setScale(scaleX, scaleY);

        Polygon poly2 = other.getBoundaryPolygon();
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return false;
        }

        return Intersector.overlapConvexPolygons(poly1, poly2);
    }

    public static Rectangle getWorldBounds(){
        return worldBounds;
    }


}
