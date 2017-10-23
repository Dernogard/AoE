package com.mygdx.Screen.Intros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.Tools.Fonts;
import com.mygdx.game.MyGdxGame;
import com.mygdx.menu.LevelMenu;

/**
 * Created by velz on 28.07.17.
 */
public class Win implements Screen {

    private Stage stage;
    private MyGdxGame game;
    private Sound winSound;
    private static int score;

    public Win (MyGdxGame game) {

        this.game = game;
        score = Twilight.getScore();

        Viewport viewport = new FitViewport(MyGdxGame.V_WIDTH , MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        Label.LabelStyle font = new Label.LabelStyle();

        font.font = new Fonts().createFont(50, Color.WHITE, "anime");
        Label gameOverLabel = new Label("УРОВЕНЬ ПРОЙДЕН!", font);

        font.font = new Fonts().createFont(30, Color.WHITE, "anime");
        Label playAgainLabel = new Label("ВАШИ ОЧКИ: ", font);
        Label score = new Label(String.format("%6d", Win.score), font);

        font.font = new Fonts().createFont(20, Color.WHITE, "anime");
        Label AlphaText = new Label("К сожалению, доступно только два уровня. Больше уровней будет добавлено позже. Для выхода нажми SPACE.", font);
        AlphaText.setWrap(true);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(gameOverLabel);
        table.add();

        table.row();
        table.add(playAgainLabel).padTop(60f);
        table.add(score).left().padTop(60f);

        table.row();
        table.add(AlphaText).padTop(300f).colspan(2).left().size(700, 200);

        stage.addActor(table);

        winSound = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/smb_stage_clear.wav"));

        if(MyGdxGame.isVolumeOn())
            winSound.play(MyGdxGame.getVOLUME());
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
           game.setScreen(new LevelMenu(game));
           dispose();
        }

        stage.getCamera().update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();

        }

    @Override
    public void resize(int width, int height) {stage.getViewport().update(width, height); }

    @Override
    public void pause() {    }

    @Override
    public void resume() {   }

    @Override
    public void hide() { }

    public static void setScore (int x) {
        Win.score = x;
    }

    @Override
    public void dispose() {
        stage.dispose();
        winSound.dispose();
    }
}
