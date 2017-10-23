package com.mygdx.Screen.Dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Tools.Fonts;
import com.mygdx.game.MyGdxGame;

public class AllDialog implements Screen{

    public Stage stage;
    protected AllLevels lvl;

    private int indexDialog;
    String textDialog;

    private Label dialogText;
    Image iconLeft, iconRight, bubble;
    Skin skinIcon;

    AllDialog (AllLevels lvl) {

    this.lvl = lvl;

        Viewport viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, lvl.getGame().batch);

        TextureAtlas guiAtlas = new TextureAtlas(Gdx.files.internal("Atlas/dialog_atlas.pack"));

        skinIcon = new Skin();
        skinIcon.addRegions(guiAtlas);

        iconLeft = new Image();
        iconRight = new Image();
        bubble = new Image();

        Label.LabelStyle styleDialog = new Label.LabelStyle();
        styleDialog.font = new Fonts().createFont(20,Color.BLACK, "anime");

         dialogText = new Label("", styleDialog);
         dialogText.setWrap(true);

        changeDialog(0);

        //Таблица разметки графики
        Table table = new Table();
        table.setSize(930, 300);
        table.setPosition(MyGdxGame.V_WIDTH / 2.5f - table.getWidth()/3, MyGdxGame.V_HEIGHT / 1.7f);
        table.center();

        table.add(iconLeft).size(200,200).align(Align.bottom).expandY();
        table.add(bubble).size(530, 250).align(Align.top).expandY();
        table.add(iconRight).size(200,200).align(Align.bottom).expandY();

        //Таблица разметки текста
        Table table2 = new Table();
        table2.setSize(930, 300);
        table2.setPosition(MyGdxGame.V_WIDTH / 2.5f - table.getWidth()/3, MyGdxGame.V_HEIGHT / 1.7f);
        table2.center();

        table2.add().size(200,300);
        table2.add(dialogText).size(430, 200).align(Align.top);
        table2.add().size(200,300);

        stage.addActor(table);
        stage.addActor(table2);

}

    public void update () {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            indexDialog++;
            changeDialog(indexDialog);
        }

        dialogText.setText(textDialog);

    }

    public void changeDialog (int i) {}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
    public void resize(int width, int height) {stage.getViewport().update(width, height);}

    public void dispose () {
        stage.dispose();
        skinIcon.dispose();
    }
}
