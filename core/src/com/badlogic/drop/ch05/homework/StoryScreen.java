package com.badlogic.drop.ch05.homework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class StoryScreen extends BaseScreen{
    Scene scene;
    Background background;
    Kelsoe kelsoe;
    DialogBox dialogBox;
    BaseActor continueKey;
    Table buttonTable;
    BaseActor theEnd;

    @Override
    public void initialize() {
        background = new Background(0,0,mainStage);
        background.setOpacity(0);
        BaseActor.setWorldBounds(background);

        kelsoe = new Kelsoe(0,0,mainStage);

        dialogBox = new DialogBox(0,0, uiStage);
        dialogBox.setFontScale(0.6f);
        dialogBox.setDialogSize(600, 150);
        dialogBox.setBackgroundColor(new Color(0.2f,0.2f,0.2f,1));
        dialogBox.setVisible(false);

        continueKey = new BaseActor(0,0, mainStage);
        continueKey.loadTexture("homework/key-C.png");
        continueKey.setSize(32,32);
        continueKey.setVisible(false);

        dialogBox.addActor(continueKey);
        continueKey.setPosition(dialogBox.getWidth() - continueKey.getSpeed(), 0);

        buttonTable = new Table();
        buttonTable.setVisible(true);

        uiTable.add().expandY();
        uiTable.row();
        uiTable.add(buttonTable);
        uiTable.row();
        uiTable.add(dialogBox);

        theEnd = new BaseActor(0,0,mainStage);
        theEnd.loadTexture("homework/the-end.png");
        theEnd.centerAtActor(background);
        theEnd.setScale(2);
        theEnd.setOpacity(0);

        scene = new Scene();
        mainStage.addActor(scene);
        hallway();

    }

    @Override
    public void update(float delta) {

    }

    public void hallway(){
        background.setAnimation(background.hallway);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));

        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));

        addTextSequence("My name is Kelsoe Kismet. I am a student at Aureus Ludus Academy.");
        addTextSequence("I can be a little forgetful sometimes. Right now, I'm looking for my homework.");

        scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1)));
        scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));

        scene.addSegment(new SceneSegment(background, Actions.run(()->{classroom();})));
        scene.start();
    }

    public void classroom(){
        scene.clearSegments();
        background.setAnimation(background.classroom);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));

        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));

        addTextSequence("This is my classroom. My homework isn't here, though");
        addTextSequence("Where should I look for my homework next?");

        scene.addSegment(new SceneSegment(buttonTable, Actions.show()));

        //set up options
        TextButton scienceLabButton = new TextButton("Look in the science Lab", BaseGame.textButtonStyle);
        scienceLabButton.addListener((Event e) ->{
            if (!(e instanceof InputEvent) ||
                    !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                return false;

            scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
            addTextSequence("That's a great idea, I'll check the science lab");
            scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
            scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1)));
            scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
            scene.addSegment(new SceneSegment(background, Actions.run(()->{scienceLab();})));

            return false;
        });

        TextButton libraryButton = new TextButton("Look in the Library", BaseGame.textButtonStyle);
        libraryButton.addListener((Event e) ->{
            if (!(e instanceof InputEvent) ||
                    !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                return false;

            scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
            addTextSequence("That's a great idea. Maybe I left it in the library.");
            scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
            scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1)));
            scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
            scene.addSegment(new SceneSegment(background, Actions.run(()->{library();})));
            return false;
        });

        buttonTable.clearChildren();
        buttonTable.add(scienceLabButton);
        buttonTable.row();
        buttonTable.add(libraryButton);

        scene.start();
    }

    public void scienceLab(){
        scene.clearSegments();

        background.setAnimation(background.scienceLab);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));

        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));

        addTextSequence("This is the science lab.");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.sad)));
        addTextSequence("My homework isn't here, though.");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)));
        addTextSequence("Now where should I go?");

        scene.addSegment(new SceneSegment(buttonTable, Actions.show()));

        // set up options
        TextButton classroomButton = new TextButton("Return to the classroom", BaseGame.textButtonStyle);
        classroomButton.addListener((Event e) ->{
            if (!(e instanceof InputEvent) ||
                    !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                return false;

            scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
            addTextSequence("Maybe someone found it and put it into the classroom. I'll go check.");
            scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
            scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1)));
            scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
            scene.addSegment(new SceneSegment(background, Actions.run(()->{classroom();})));
            return false;
        });


        TextButton libraryButton = new TextButton("Look in the Library", BaseGame.textButtonStyle);
        classroomButton.addListener((Event e) ->{
            if (!(e instanceof InputEvent) ||
                    !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                return false;

            scene.addSegment(new SceneSegment(buttonTable, Actions.hide()));
            addTextSequence("That's a great idea. Maybe I left it in the library.");
            scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
            scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1)));
            scene.addSegment(new SceneSegment(background, Actions.fadeOut(1)));
            scene.addSegment(new SceneSegment(background, Actions.run(()->{library();})));
            return false;
        });

        buttonTable.clearChildren();
        buttonTable.add(classroomButton);
        buttonTable.row();
        buttonTable.add(libraryButton);

        scene.start();

    }

    public void library(){
        scene.clearSegments();

        background.setAnimation(background.library);
        dialogBox.setText("");
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0));

        scene.addSegment(new SceneSegment(background, Actions.fadeIn(1)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1)));
        scene.addSegment(new SceneSegment(dialogBox, Actions.show()));

        addTextSequence("This is the library.");
        addTextSequence("Let me check the table where I was working earlier ...");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.lookRight)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenRight(2)));
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)));
        addTextSequence("Aha! Here it is!");
        scene.addSegment(new SceneSegment(kelsoe, SceneActions.moveToScreenCenter(0.5f)));
        addTextSequence("Thanks for helping me find it!");
        scene.addSegment(new SceneSegment(dialogBox, Actions.hide()));
        scene.addSegment(new SceneSegment(theEnd, Actions.fadeIn(4)));
        scene.addSegment(new SceneSegment(background, Actions.delay(10)));
        scene.addSegment(new SceneSegment(background, Actions.run(()->{BaseGame.setActiveScreen(new MenuScreen());})));

        scene.start();
    }

    public boolean keyDown(int keyCode){
        if(keyCode == Input.Keys.C)
            scene.loadNextSegment();
        return false;
    }

    public void addTextSequence(String s){
        scene.addSegment(new SceneSegment(dialogBox, SceneActions.typewriter(s)));
        scene.addSegment(new SceneSegment(continueKey, Actions.show()));
        scene.addSegment(new SceneSegment(background, SceneActions.pause()));
        scene.addSegment(new SceneSegment(continueKey, Actions.hide()));
    }
}
