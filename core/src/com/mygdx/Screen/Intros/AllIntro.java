package com.mygdx.Screen.Intros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 05.08.17.
 */
public abstract class AllIntro implements Screen {

    protected Stage stage;
    protected MyGdxGame game;

    protected int index;

    Image introImage;
    Label textDialog;
    boolean beforeChoice, afterChoice, choice, choiceWindowsView;

    AllIntro (MyGdxGame game) {
        this.game = game;
        Viewport viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        beforeChoice = true;
        afterChoice = false;
        choice = false;
        choiceWindowsView = false;

        Gdx.input.setInputProcessor(stage);
    }

    public void update (float dt) {
        if (beforeChoice || afterChoice)
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                index++;
                changeDialog(index);
            }

            if (choice) giveChoice();
    }

    public abstract void giveChoice ();

    public abstract void changeDialog (int index);

    @Override
    public void render(float delta) {
        //update(delta);

        stage.getCamera().update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
