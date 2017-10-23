package com.mygdx.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.Screen.Levels.Level_1;
import com.mygdx.Screen.Levels.Level_2;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 03.07.17.
 */
public class LevelMenu implements Screen {

    private MyGdxGame game1;
    private Stage stage;

    private boolean lockApple, lockFlutter, lockPinkie, lockRaindox, lockRarity, lockTwi;
    private ImageButton applejackIcon, fluttershyIcon, pinkieIcon,rainbowIcon, rarityIcon, twilightIcon;

    private Sprite lock;

    public LevelMenu (MyGdxGame game) {

        this.game1 = game;

        //if (!MainMenu.music.isPlaying() && MyGdxGame.isVolumeOn()) MainMenu.music.play();

        stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM), game1.batch);


        lockApple = false;
        lockFlutter = true;
        lockPinkie = true;
        lockRaindox = true;
        lockRarity = false;
        lockTwi = true;

        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("Atlas/icons.pack")));

        lock = new Sprite(skin.getRegion("locked-padlock"));

        ImageButton.ImageButtonStyle imgStyle = new ImageButton.ImageButtonStyle();

        imgStyle.up = skin.getDrawable("Applejack_Gray");
        imgStyle.down = skin.getDrawable("Applejack");
        imgStyle.checked = skin.getDrawable("Applejack");

        applejackIcon = new ImageButton(imgStyle);
        applejackIcon.setSize(applejackIcon.getWidth() / MyGdxGame.PPM, applejackIcon.getHeight() / MyGdxGame.PPM);
        applejackIcon.addListener(new ClickListener() {


                                      @Override
                                      public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                          super.touchUp(event, x, y, pointer, button);
                                          game1.setScreen(new Level_1(game1));
                                          dispose();

                                      }

                                        @Override
                                        public boolean mouseMoved(InputEvent event, float x, float y) {
                                        applejackIcon.setChecked(true);
                                        return super.mouseMoved(event, x, y);
                                        }
                                  }
        );

        ImageButton.ImageButtonStyle imgStyle2 = new ImageButton.ImageButtonStyle();

        imgStyle2.up = skin.getDrawable("Rainbow Dash_Gray");
        imgStyle2.down = skin.getDrawable("Rainbow Dash");
        imgStyle2.checked = skin.getDrawable("Rainbow Dash");

        rainbowIcon = new ImageButton(imgStyle2);
        rainbowIcon.setSize(rainbowIcon.getWidth() / MyGdxGame.PPM, rainbowIcon.getHeight() / MyGdxGame.PPM);
        rainbowIcon.addListener(new ClickListener() {

                                      @Override
                                      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                          System.out.println("Рэйнбоу");
                                          return true;
                                      }
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                rainbowIcon.setChecked(true);
                return super.mouseMoved(event, x, y);
            }
                                  }
        );

        ImageButton.ImageButtonStyle imgStyle3 = new ImageButton.ImageButtonStyle();
        imgStyle3.up = skin.getDrawable("Twilight Sparkle_Gray");
        imgStyle3.down = skin.getDrawable("Twilight Sparkle");
        imgStyle3.checked = skin.getDrawable("Twilight Sparkle");

        twilightIcon = new ImageButton(imgStyle3);
        twilightIcon.setSize(twilightIcon.getWidth() / MyGdxGame.PPM, twilightIcon.getHeight() / MyGdxGame.PPM);
        twilightIcon.addListener(new ClickListener() {

                                    @Override
                                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                        System.out.println("Твайлайт");
                                        return true;
                                    }
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                twilightIcon.setChecked(true);
                return super.mouseMoved(event, x, y);
            }
                                }
        );

        ImageButton.ImageButtonStyle imgStyle4 = new ImageButton.ImageButtonStyle();
        imgStyle4.up = skin.getDrawable("Rarity_Gray");
        imgStyle4.down = skin.getDrawable("Rarity");
        imgStyle4.checked = skin.getDrawable("Rarity");

        rarityIcon = new ImageButton(imgStyle4);
        rarityIcon.setSize(rarityIcon.getWidth() / MyGdxGame.PPM, rarityIcon.getHeight() / MyGdxGame.PPM);
        rarityIcon.addListener(new ClickListener() {

                                     @Override
                                     public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                         game1.setScreen(new Level_2(game1));
                                         dispose();
                                         return true;
                                     }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                rarityIcon.setChecked(true);
                return super.mouseMoved(event, x, y);
            }
                                 }
        );

        ImageButton.ImageButtonStyle imgStyle5 = new ImageButton.ImageButtonStyle();
        imgStyle5.up = skin.getDrawable("Pinkie Pie_Gray");
        imgStyle5.down = skin.getDrawable("Pinkie Pie");
        imgStyle5.checked = skin.getDrawable("Pinkie Pie");

        pinkieIcon = new ImageButton(imgStyle5);
        pinkieIcon.setSize(pinkieIcon.getWidth() / MyGdxGame.PPM, pinkieIcon.getHeight() / MyGdxGame.PPM);
        pinkieIcon.addListener(new ClickListener() {

                                   @Override
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                       System.out.println("Пинки");
                                       return true;
                                   }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                pinkieIcon.setChecked(true);
                return super.mouseMoved(event, x, y);
            }
                               }
        );

        ImageButton.ImageButtonStyle imgStyle6 = new ImageButton.ImageButtonStyle();
        imgStyle6.up = skin.getDrawable("Fluttershy_Gray");
        imgStyle6.down = skin.getDrawable("Fluttershy");
        imgStyle6.checked = skin.getDrawable("Fluttershy");

        fluttershyIcon = new ImageButton(imgStyle6);
        fluttershyIcon.setSize(fluttershyIcon.getWidth() / MyGdxGame.PPM, fluttershyIcon.getHeight() / MyGdxGame.PPM);
        fluttershyIcon.addListener(new ClickListener() {

                                   @Override
                                   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                       System.out.println("Флаттершай");
                                       return true;
                                   }
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                fluttershyIcon.setChecked(true);
                return super.mouseMoved(event, x, y);
            }

                                   }
        );


        ImageButton.ImageButtonStyle imgStyle7 = new ImageButton.ImageButtonStyle();
        imgStyle7.up = skin.getDrawable("Lyra_Back");
        imgStyle7.down = skin.getDrawable("Lyra_Back");
        imgStyle7.checked = skin.getDrawable("Lyra_Back");

        ImageButton lyraIconBack = new ImageButton(imgStyle7);
        lyraIconBack.setSize(lyraIconBack.getWidth() / MyGdxGame.PPM, lyraIconBack.getHeight() / MyGdxGame.PPM);
        lyraIconBack.addListener(new ClickListener() {

                                       @Override
                                       public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                           dispose();
                                           game1.setScreen(new MainMenu(game1));
                                           return true;
                                       }
                                   }
        );


        Pixmap pinkPixel = new Pixmap(1,1,Pixmap.Format.RGB888);
        pinkPixel.setColor(Color.PINK);
        pinkPixel.fill();
        Image background = new Image(new Texture(pinkPixel));
        pinkPixel.dispose();
        background.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

                                    @Override
                                   public boolean mouseMoved(InputEvent event, float x, float y) {
                                       applejackIcon.setChecked(false);
                                       fluttershyIcon.setChecked(false);
                                       pinkieIcon.setChecked(false);
                                       rainbowIcon.setChecked(false);
                                       rarityIcon.setChecked(false);
                                       twilightIcon.setChecked(false);
                                       return super.mouseMoved(event, x, y);
                                   }
                               }
        );


        background.setSize(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT/ MyGdxGame.PPM);
        stage.addActor(background);

        lyraIconBack.setPosition((MyGdxGame.V_WIDTH / MyGdxGame.PPM) - lyraIconBack.getWidth(), 0);

        applejackIcon.setPosition((MyGdxGame.V_WIDTH / 4f) / MyGdxGame.PPM , (MyGdxGame.V_HEIGHT / 1.8f) / MyGdxGame.PPM);
        fluttershyIcon.setPosition(applejackIcon.getX() + 306 / MyGdxGame.PPM, applejackIcon.getY());
        pinkieIcon.setPosition(fluttershyIcon.getX() + 306 / MyGdxGame.PPM, applejackIcon.getY());

        rarityIcon.setPosition(applejackIcon.getX(), applejackIcon.getY() - 306 / MyGdxGame.PPM);
        rainbowIcon.setPosition(fluttershyIcon.getX(), rarityIcon.getY());
        twilightIcon.setPosition(pinkieIcon.getX(), rarityIcon.getY());


        stage.addActor(applejackIcon);
        stage.addActor(fluttershyIcon);
        stage.addActor(pinkieIcon);
        stage.addActor(rarityIcon);
        stage.addActor(rainbowIcon);
        stage.addActor(twilightIcon);

        stage.addActor(lyraIconBack);

        Gdx.input.setInputProcessor(stage);

        MyGdxGame.setNumCheckPoint(0); //Сбрасываем номер чек-поинта при выходе в меню уровня


    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getCamera().update();

        game1.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();

        game1.batch.begin();

        drawLock(); //Вешаем замки

        game1.batch.end();

    }

    public void update () {}

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        game1.music.stop();
    }

    private void drawLock () {
        if (lockApple)
            game1.batch.draw(lock, applejackIcon.getX(), applejackIcon.getY(),0.3f,0.3f);

        if (lockFlutter)
            game1.batch.draw(lock, fluttershyIcon.getX(), fluttershyIcon.getY(),0.3f,0.3f);

        if (lockPinkie)
            game1.batch.draw(lock, pinkieIcon.getX(), pinkieIcon.getY(),0.3f,0.3f);

        if (lockRarity)
            game1.batch.draw(lock, rarityIcon.getX(), rarityIcon.getY(),0.3f,0.3f);

        if (lockRaindox)
            game1.batch.draw(lock, rainbowIcon.getX(), rainbowIcon.getY(),0.3f,0.3f);

        if (lockTwi)
            game1.batch.draw(lock, twilightIcon.getX(), twilightIcon.getY(),0.3f,0.3f);

    }
}
