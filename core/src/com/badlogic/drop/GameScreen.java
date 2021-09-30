package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Drop game;

    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> rainDrops;
    long lastDropTime;
    int dropsGathered;
    int dropMissed;

    public GameScreen(Drop game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // start the playback of the background music immediately
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        // create a Rectange to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800/2 -64 /2; // center the bucket horizontally
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and sqawn the first raindrop
        rainDrops = new Array<>();
        dropsGathered = 0;
        dropMissed = 0;

        spawnRaindrop();
    }

    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;

        rainDrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        //rainMusic.play();
    }

    @Override
    public void render(float delta) {
// clear the screen with dark blue color.
        // The arguments to clear are the red, green blue and alpha component in the range[0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0,0,0.2f,1);

        // tell the camera to update its matrices
        camera.update();

        // tell the spriteBatch to render in the coordinate system specified by the camera
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and all drops
        game.batch.begin();
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle raindrop : rainDrops){
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.font.draw(game.batch, String.format("Gathered: %d", dropsGathered), 0, 380);
        game.font.draw(game.batch, String.format("Missed: %d", dropMissed), 600, 380);
        game.batch.end();

        // process user input
        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64/2;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))  bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800-64) bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that it hit the bucket. In the latter case we play back
        // a sound effect as well.
        for(Iterator<Rectangle> iter = rainDrops.iterator(); iter.hasNext();){
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0){
                iter.remove();
                dropMissed ++;
            }
            if(raindrop.overlaps(bucket)){
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
