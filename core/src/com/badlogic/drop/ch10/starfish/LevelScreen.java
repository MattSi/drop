package com.badlogic.drop.ch10.starfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private boolean win;
    private Label starfishLabel;
    private DialogBox dialogBox;


    @Override
    public void initialize() {



        starfishLabel = new Label("Starfish Left:", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);
        uiStage.addActor(starfishLabel);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("starfish/undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);


        TilemapActor tma = new TilemapActor("starfish/map.tmx", mainStage);
        for(MapObject obj : tma.getTileList("Starfish")){
            MapProperties props = obj.getProperties();
            new Starfish((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getTileList("Rock")){
            MapProperties props = obj.getProperties();
            new Rock((float) props.get("x"), (float) props.get("y"), mainStage);
        }


        for(MapObject obj : tma.getTileList("Sign")){
            MapProperties props = obj.getProperties();
            Sign s = new Sign((float) props.get("x"), (float) props.get("y"), mainStage);
            s.setText((String)props.get("message"));
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties props = startPoint.getProperties();
        turtle = new Turtle((float) props.get("x"), (float) props.get("y"), mainStage);

        uiStage.addActor(restartButton);


        dialogBox = new DialogBox(0,0,uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
        uiTable.row();
        uiTable.add(dialogBox).colspan(3);

        restartButton.addListener(
                (Event e) ->{
           if( !(e instanceof InputEvent) ||
            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
               return false;

           StarfishGame.setActiveScreen(new LevelScreen());
                    return false;
                });
    }

    @Override
    public void update(float dt) {
        for(BaseActor rockActor : BaseActor.getList(mainStage, "com.badlogic.drop.ch10.starfish.Rock"))
            turtle.preventOverlap(rockActor);

        for(BaseActor starfishActor : BaseActor.getList(mainStage, "com.badlogic.drop.ch10.starfish.Starfish")){
            Starfish starfish = (Starfish) starfishActor;

            if(turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();
                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.75f);
            }
        }

        if(BaseActor.count(mainStage, "com.badlogic.drop.ch10.starfish.Starfish") == 0 && !win){
            win = true;
            BaseActor youWinMessage = new BaseActor(0,0, mainStage);
            youWinMessage.loadTexture("starfish/you-win.png");
            youWinMessage.centerAtPosition(400,300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        starfishLabel.setText("Starfish Left:" + BaseActor.count(mainStage, "com.badlogic.drop.ch10.starfish.Starfish"));

        for(BaseActor signActor : BaseActor.getList(mainStage, "com.badlogic.drop.ch10.starfish.Sign")){
            Sign sign = (Sign)signActor;
            turtle.preventOverlap(sign);
            boolean nearby = turtle.isWithinDistance(4, sign);

            if(nearby && !sign.isViewing()){
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }
            if(!nearby && sign.isViewing()){
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }
    }
}
