package com.mygdx.Screen.Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.Tools.Fonts;
import com.mygdx.game.MyGdxGame;


/**
 * Created by velz on 27.07.17.
 */
public class Hud {

    public Stage stage;

    private int score;
    private int lives;

    private Label scoreLabel;

    private Image imgHeart;
    private TextureAtlas buttonAtlas;

    public Hud (SpriteBatch sb) {

        score = 0;
        lives = 3;

        buttonAtlas = new TextureAtlas(Gdx.files.internal("Atlas/hearts.pack"));

        imgHeart = new Image(buttonAtlas.findRegion("threeheart"));

        Viewport viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();  //Табличная разметка интерфейса
        table.top();
        table.setFillParent(true);

        Label.LabelStyle font = new Label.LabelStyle();
        font.font = new Fonts().createFont(36, Color.WHITE, "anime");

        scoreLabel = new Label(String.format("%6d", score), font);
        Label numberZone = new Label ("", font);
        Label textLive = new Label ("Жизни:   ",font);
        Label textScore = new Label ("Очки:   ", font);

        table.add(textLive).right().padTop(10).padLeft(60);
        table.add(imgHeart).expandX().padTop(10);

        table.add(numberZone).expandX().padTop(10);

        table.add(textScore).right().expandX().padTop(10);
        table.add(scoreLabel).left().expandX().padTop(10);

        stage.addActor(table);
    }

    public void update () {
        score = Twilight.getScore();
        scoreLabel.setText(String.format("%6d", score));

        if (lives != Twilight.getLives())
            changeLives();
    }

    private void changeLives () {

        lives = Twilight.getLives();

        switch (lives){
            case 1:
                imgHeart.setDrawable(new Image(buttonAtlas.findRegion("oneheart")).getDrawable());
                break;
            case 2:
                imgHeart.setDrawable(new Image(buttonAtlas.findRegion("twoheart")).getDrawable());
                break;
            case 3:
            default:
                imgHeart.setDrawable(new Image(buttonAtlas.findRegion("threeheart")).getDrawable());
                break;
        }
    }

    public void dispose () {
        stage.dispose();
        buttonAtlas.dispose();
    }

}
