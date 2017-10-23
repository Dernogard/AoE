package com.mygdx.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.Tools.Fonts;
import com.mygdx.game.MyGdxGame;


public class Credits implements Screen {

    private MyGdxGame game;
    private Stage stage;
    private OrthographicCamera camera;


    Credits (MyGdxGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, camera));

        Label.LabelStyle fontZagolovok = new Label.LabelStyle();
        fontZagolovok.font = new Fonts().createFont(50, Color.RED, "anime");

        Label.LabelStyle fontCredits = new Label.LabelStyle();
        fontCredits.font = new Fonts().createFont(30, Color.WHITE, "fontinBI");

        Label zagolovokMain = new Label("НАД ИГРОЙ РАБОТАЛИ:", fontZagolovok);

        Array<Label> developersArray = new Array<>();
        developersArray.add(new Label("Программист, сценарист на все руки мастерист - Nogard (patrikon@mail.ru)", fontCredits));
        developersArray.add(new Label("Права на персонажей из MLP принадлежат Hasbro", fontCredits));
        developersArray.add(new Label("Звуки и графика взяты из свободного доступа", fontCredits));

        Label zagolovokSpasibok = new Label("БЛАГОДАРНОСТЬ ЗА РЕСУРСЫ:", fontZagolovok);

        Array<Label> thanksArray = new Array<>();
        thanksArray.add(new Label("Сайту OpenGameArt.org", fontCredits));
        thanksArray.add(new Label("Музыка из меню «Woodland Fantasy» by Matthew Pablo www.matthewpablo.com", fontCredits));
        thanksArray.add(new Label("Музыка первого и второго уровней - opengameart.org/users/djsaryon", fontCredits));
        thanksArray.add(new Label("Звук взрыва opengameart.org/users/qubodup", fontCredits));
        thanksArray.add(new Label("Иное звуковое сопровождение взято из игры \"Mario для Денди.\"", fontCredits));
        thanksArray.add(new Label("", fontCredits));
        thanksArray.add(new Label("", fontCredits));
        thanksArray.add(new Label("Враг \"Летучая мышь\" статическая картинка  - gurugrendo.deviantart.com", fontCredits));
        thanksArray.add(new Label("Враг \"Летучая мышь\" анимация полета  - silvervectors.deviantart.com", fontCredits));
        thanksArray.add(new Label("Враг \"Пчеломедведь\" статическая картинка - anonimowybrony.deviantart.com", fontCredits));
        thanksArray.add(new Label("Враги \"Призрак\", \"Кокатрикс\", \"Змея\" статические картинки - opengameart.org/users/charlesgabriel", fontCredits));
        thanksArray.add(new Label("Враг \"Странный монстр 1 уровня\" статическая картинка - opengameart.org/users/manveru", fontCredits));
        thanksArray.add(new Label("Анимация взрыва - opengameart.org/users/j-robot", fontCredits));
        thanksArray.add(new Label("Прыжок Твайлайт статическая картинка - starshinecelestalis.deviantart.com", fontCredits));
        thanksArray.add(new Label("Стоящая Твайлайт статическая картинка - thorinair.deviantart.com", fontCredits));
        thanksArray.add(new Label("Анимация бега Твайлайт - warpout.deviantart.com", fontCredits));
        thanksArray.add(new Label("Различные вектора персонажей - vector-mlp.deviantart.com/gallery/", fontCredits));
        thanksArray.add(new Label("Иконки в меню выбора уровня - atomicgreymon.deviantart.com", fontCredits));
        thanksArray.add(new Label("Графическая составляющая уровней - www.gameart2d.com/", fontCredits));


        Table table = new Table();
        //table.setDebug(true);
        table.setFillParent(true);
        table.padTop(2500f);


        table.add(zagolovokMain).expandX().spaceBottom(40f);
        table.row();

        for (int i = 0; i < developersArray.size; i++) {
            table.add(developersArray.get(i)).expandX().spaceBottom(15f);
            table.row();
        }

        table.add(zagolovokSpasibok).expandX().spaceBottom(40f).spaceTop(40f);
        table.row();

        for (int i = 0; i < thanksArray.size; i++) {
            table.add(thanksArray.get(i)).expandX().spaceBottom(15f);
            table.row();
        }

        stage.addActor(table);

    }

    @Override
    public void show() {    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getCamera().update();

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    public void update (float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
            dispose();
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            camera.position.y = camera.position.y - 80*dt;
        }
    }

    @Override
    public void resize(int width, int height) {  stage.getViewport().update(width, height);  }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

    @Override
    public void hide() {    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
