package com.mygdx.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Screen.Levels.Level_1;
import com.mygdx.Screen.Levels.Level_2;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.Tools.Fonts;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 27.07.17.
 */
public class IngameMenu {

    public Stage stage;
    private TextureAtlas guiAtlas;

    public IngameMenu (AllLevels lvl) {

        Viewport viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, lvl.getGame().batch);

        guiAtlas = new TextureAtlas(Gdx.files.internal("Atlas/guiAtlas.pack"));
        ImageTextButton resetBtn, exitBtn, titleButton, lvlBtn;


// Меню, задник начало
        Table table = new Table();  //Табличная разметка интерфейса
        table.setSize(500, 400);
        table.setPosition(MyGdxGame.V_WIDTH / 2 - table.getWidth()/4, MyGdxGame.V_HEIGHT / 3);

        table.center();

        Image backMenu = new Image(guiAtlas.findRegion("ScreenMenuFon"));
        table.setBackground(backMenu.getDrawable());
//Меню, задник конец


//Меню кнопки, начало
        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("Atlas/guiAtlas.pack")));

        //-----------------
        ImageTextButton.ImageTextButtonStyle imgStyle1 = new ImageTextButton.ImageTextButtonStyle();
        imgStyle1.down = skin.getDrawable("MenuSelected");
        imgStyle1.checked = skin.getDrawable("MenuSelected");
        imgStyle1.font = new Fonts().createFont(30, Color.GRAY, "fontinI");
        resetBtn = new ImageTextButton("Начать с начала", imgStyle1);
        resetBtn.setSize(table.getWidth(), 20);
        resetBtn.addListener(new ClickListener() {
            @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        super.clicked(event, x, y);
                                             resetLevel(lvl.getNumLvl(), lvl.getGame());
                                            lvl.dispose();
                                        }

                                    @Override
                                    public boolean mouseMoved(InputEvent event, float x, float y) {
                                        resetBtn.setChecked(true);
                                        return super.mouseMoved(event, x, y);
                                    }

                                }
        );

        //-----------------
        ImageTextButton.ImageTextButtonStyle imgStyle2 = new ImageTextButton.ImageTextButtonStyle();
        imgStyle2.checked = skin.getDrawable("MenuSelected");
        imgStyle2.down = skin.getDrawable("MenuSelected");
        imgStyle2.font = new Fonts().createFont(30, Color.GRAY, "fontinI");
        lvlBtn = new ImageTextButton("К выбору уровня", imgStyle2);
        lvlBtn.setSize(table.getWidth(), 20);
        lvlBtn.addListener(new ClickListener() {
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
                                     super.clicked(event, x, y);
                                     lvl.getGame().setScreen(new LevelMenu(lvl.getGame()));
                                     lvl.dispose();
                                 }

                                 @Override
                                 public boolean mouseMoved(InputEvent event, float x, float y) {
                                     lvlBtn.setChecked(true);
                                     return super.mouseMoved(event, x, y);
                                 }
                             }
        );

        //-----------------
        ImageTextButton.ImageTextButtonStyle imgStyle3 = new ImageTextButton.ImageTextButtonStyle();
        imgStyle3.checked = skin.getDrawable("MenuSelected");
        imgStyle3.down = skin.getDrawable("MenuSelected");
        imgStyle3.font = new Fonts().createFont(30, Color.GRAY, "fontinI");
        exitBtn = new ImageTextButton("Выход из игры", imgStyle3);
        exitBtn.setSize(table.getWidth(), 20);
        exitBtn.addListener(new ClickListener() {
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
                                     super.clicked(event, x, y);
                                     Gdx.app.exit();
                                 }

                                 @Override
                                 public boolean mouseMoved(InputEvent event, float x, float y) {
                                     exitBtn.setChecked(true);
                                     return super.mouseMoved(event, x, y);
                                 }
                             }
        );

        //-----------------
        ImageTextButton.ImageTextButtonStyle imgStyle = new ImageTextButton.ImageTextButtonStyle();
        imgStyle.up = skin.getDrawable("ScreenMenuTitle");
        imgStyle.font = new Fonts().createFont(40, Color.BROWN, "fontinBI");
        titleButton = new ImageTextButton("МЕНЮ", imgStyle);

        //-----------------
        Table table2 = new Table();  //Табличная разметка интерфейса
        table2.setSize(table.getWidth(), table.getHeight());
        table2.top().left();
        table2.setFillParent(true);

        table2.add(titleButton).size(table.getWidth(), table.getWidth()/4.42f);  // Заголовок меню

        table2.row();
        table2.add(resetBtn).fillX().spaceTop(20f); //Заново

        table2.row();
        table2.add(lvlBtn).fillX().spaceTop(20f); //Выбор уровня

        table2.row();
        table2.add(exitBtn).fillX().spaceTop(20f); //Выход из игры

//Меню, кнопки, конец


//Меню костыль, для снятия выделений с кнопок меню
        Table table13 = new Table();
        table13.center();
        table13.setFillParent(true);
        Label.LabelStyle font = new Label.LabelStyle();
        font.font = new Fonts().createFont(100, Color.WHITE, "anime");
        Label pf = new Label("", font);
        pf.addListener(new ClickListener() {

                               @Override
                               public boolean mouseMoved(InputEvent event, float x, float y) {
                                   resetBtn.setChecked(false);
                                   exitBtn.setChecked(false);
                                   lvlBtn.setChecked(false);
                                   return super.mouseMoved(event, x, y);
                               }
                           }
        );

        table13.add(pf).size(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
//Конец меню-костыля

        table.add(table2).size(table.getWidth(), table.getHeight());

        stage.addActor(table13);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    private void resetLevel (int numLvl, MyGdxGame game) {
        switch (numLvl){
            case 1:
                game.setScreen(new Level_1(game));

                break;
            case 2:
                game.setScreen(new Level_2(game));
                break;
        }
    }

    public void update () { }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }


    public void dispose () {
        stage.dispose();
        guiAtlas.dispose();
    }
}
