package com.badlogic.drop.ch05.homework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class BaseGame extends Game {
    private static BaseGame game;
    public static Label.LabelStyle labelStyle;
    public static TextButton.TextButtonStyle textButtonStyle;

    public BaseGame(){
        game = this;
    }

    public static void setActiveScreen(BaseScreen s){
        game.setScreen(s);
    }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(
                Gdx.files.internal("homework/OpenSans.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 48;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderStraight = true;
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;

        BitmapFont custFont = fontGenerator.generateFont(fontParameter);
        labelStyle.font = custFont;

        textButtonStyle = new TextButton.TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("homework/button.png"));
        NinePatch buttonPatch =new NinePatch(buttonTex, 24, 24, 24, 24);
        textButtonStyle.up = new NinePatchDrawable(buttonPatch);
        textButtonStyle.font = custFont;
        textButtonStyle.fontColor = Color.GRAY;
    }
}
