package com.mygdx.Screen.Intros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Screen.Levels.Level_1;
import com.mygdx.Screen.Levels.Level_2;
import com.mygdx.Tools.Fonts;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 28.07.17.
 */
public class GameOver implements Screen {

    private Stage stage;
    private MyGdxGame game;

    private Sprite twi;

    private Sound gameOverSound;

    private int numLvl;             //Хранит номер уровня для его перезапуска

    public GameOver(MyGdxGame game, int x, boolean evilEnd){

        this.game = game;
        numLvl = x;

        Viewport viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        if (!evilEnd) twi = new Sprite(new Texture("OtherResourse/twi_final.png"));
        else twi = new Sprite(new Texture("OtherResourse/twi_crazy.png"));

        Label.LabelStyle font = new Label.LabelStyle();

        font.font = new Fonts().createFont(75, Color.RED, "anime");
        Label gameOverLabel = new Label("GAME OVER", font);

        font.font = new Fonts().createFont(30, Color.WHITE, "anime");
        Label playAgainLabel = new Label("Нажми SPACE\n  \n   для рестарта", font);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(gameOverLabel).expandX();

        table.row();

        table.add(playAgainLabel).expandX().padTop(50f);

        stage.addActor(table);

        twi.setBounds(0,0,400,620);

        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/smb_gameover.wav"));

        if(MyGdxGame.isVolumeOn())
            gameOverSound.play(MyGdxGame.getVOLUME());
    }

    @Override
    public void show() {}

    private void getLvl () {
        switch (numLvl){
            case 1:
                game.setScreen(new Level_1(game));
                dispose();

                break;

            case 2:
                game.setScreen(new Level_2(game));
                dispose();

                break;
        }
    }

    @Override
    public void render(float delta) {

       if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
           getLvl();
        }

        stage.getCamera().update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();

        twi.setPosition(-20, -10);

        game.batch.begin();
        twi.draw(game.batch);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {stage.getViewport().update(width, height);}

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        gameOverSound.dispose();
        stage.dispose();
    }
}
