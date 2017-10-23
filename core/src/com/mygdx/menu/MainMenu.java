package com.mygdx.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 03.07.17.
 */
public class MainMenu implements Screen{

    private MyGdxGame game1;
    private Stage stage;

    public MainMenu (MyGdxGame game) {
        this.game1 = game;

        stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM), game.batch);

        ImageButton buttNewGame, buttonExit, buttonCredit;

        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("Atlas/signs.pack")));

        ImageButton.ImageButtonStyle imgStyle = new ImageButton.ImageButtonStyle();

        imgStyle.up = skin.getDrawable("SignCredit_off");
        imgStyle.down = skin.getDrawable("SignCredit_on");
        imgStyle.checked = skin.getDrawable("SignCredit_on");

        buttonCredit = new ImageButton(imgStyle);
        buttonCredit.setSize(buttonCredit.getWidth() / MyGdxGame.PPM, buttonCredit.getHeight() / MyGdxGame.PPM);

        buttonCredit.addListener(new ClickListener() {

                                    @Override
                                    public boolean mouseMoved(InputEvent event, float x, float y) {
                                        buttonCredit.setChecked(true);
                                        return super.mouseMoved(event, x, y);
                                    }

                                      @Override
                                      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                            game1.setScreen(new Credits(game1));
                                            dispose();
                                          return true;
                                      }
                                  }
        );

        ImageButton.ImageButtonStyle imgStyle2 = new ImageButton.ImageButtonStyle();

        imgStyle2.up = skin.getDrawable("SignLeft_off");
        imgStyle2.down = skin.getDrawable("SignLeft_on");
        imgStyle2.checked = skin.getDrawable("SignLeft_on");

        buttonExit = new ImageButton(imgStyle2);
        buttonExit.setSize(buttonExit.getWidth() / MyGdxGame.PPM, buttonExit.getHeight() / MyGdxGame.PPM);

        buttonExit.addListener(new ClickListener() {
                                        @Override
                                        public boolean mouseMoved(InputEvent event, float x, float y) {
                                            buttonExit.setChecked(true);
                                            return super.mouseMoved(event, x, y);
                                        }
                                     @Override
                                     public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                         Gdx.app.exit();
                                         return true;
                                     }
                                 }
        );

        ImageButton.ImageButtonStyle imgStyle3 = new ImageButton.ImageButtonStyle();

        imgStyle3.up = skin.getDrawable("SignRight_off");
        imgStyle3.down = skin.getDrawable("SignRight_on");
        imgStyle3.checked = skin.getDrawable("SignRight_on");

        buttNewGame = new ImageButton(imgStyle3);
        buttNewGame.setSize(buttNewGame.getWidth() / MyGdxGame.PPM, buttNewGame.getHeight() / MyGdxGame.PPM);

        buttNewGame.addListener(new ClickListener() {
                                        @Override
                                        public boolean mouseMoved(InputEvent event, float x, float y) {
                                            buttNewGame.setChecked(true);
                                            return super.mouseMoved(event, x, y);
                                        }

                                    @Override
                                     public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                        game1.setScreen(new LevelMenu(game1));
                                        dispose();
                                        return true;
                                     }
                                 }
        );

        Image background = new Image (new Texture("MainMenu/MainMenuImage.jpg"));
        background.addListener(new ClickListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                buttNewGame.setChecked(false);
                buttonCredit.setChecked(false);
                buttonExit.setChecked(false);
                return super.mouseMoved(event, x, y);

            }
        });

//Старт кода кнопки вкл-выкл музыки
        Skin skin2 = new Skin();
        skin2.addRegions(new TextureAtlas(Gdx.files.internal("Atlas/volume.pack")));

        ImageButton.ImageButtonStyle imgStyle4 = new ImageButton.ImageButtonStyle();

        imgStyle4.up = skin2.getDrawable("volumeOn");
        imgStyle4.down = skin2.getDrawable("volumeOff");
        imgStyle4.checked = skin2.getDrawable("volumeOff");

        ImageButton volumeBtn = new ImageButton(imgStyle4);
        volumeBtn.setChecked(!MyGdxGame.isVolumeOn());
        volumeBtn.setSize(volumeBtn.getWidth() / MyGdxGame.PPM, volumeBtn.getHeight() / MyGdxGame.PPM);
        volumeBtn.setPosition((MyGdxGame.V_WIDTH / MyGdxGame.PPM) - volumeBtn.getWidth(), 0);
        volumeBtn.addListener(new ClickListener() {

                                                    @Override
                                                    public void clicked(InputEvent event, float x, float y) {
                                                        super.clicked(event, x, y);
                                                        MyGdxGame.setVolumeOn(!MyGdxGame.isVolumeOn());
                                                    }

                                            }
        );


        background.setSize(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT/ MyGdxGame.PPM);

        buttNewGame.setPosition((MyGdxGame.V_WIDTH / (2.4f *  MyGdxGame.PPM)),(MyGdxGame.V_HEIGHT/(5.7f * MyGdxGame.PPM)));
        buttonExit.setPosition((MyGdxGame.V_WIDTH / (10f *  MyGdxGame.PPM)),(MyGdxGame.V_HEIGHT/(5f * MyGdxGame.PPM)));

        stage.addActor(background);
        stage.addActor(buttNewGame);
        stage.addActor(buttonCredit);
        stage.addActor(buttonExit);
        stage.addActor(volumeBtn);


        Gdx.input.setInputProcessor(stage);

        if (!game1.music.isPlaying() && MyGdxGame.isVolumeOn()) {
            game1.music.setVolume(MyGdxGame.getVOLUME());
            game1.music.play();
        }

    }

     @Override
    public void show() { }

    @Override
    public void render(float delta) {

        update();

        stage.getCamera().update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game1.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();

    }

    public void update () {
        if (MyGdxGame.isVolumeOn() != MyGdxGame.isVolumeNow()) {
            if (MyGdxGame.isVolumeOn())
                game1.music.play();
            else
                game1.music.pause();

            MyGdxGame.setVolumeNow(MyGdxGame.isVolumeOn());
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);

    }

    @Override
    public void pause() { }

    @Override
    public void resume() {}

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        //music.dispose();
        stage.dispose();

    }

}
