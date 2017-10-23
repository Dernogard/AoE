package com.mygdx.Screen.Intros;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.Tools.Fonts;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 05.08.17.
 */
public class Level1_Final extends AllIntro {

    private Label.LabelStyle textStyleAJ ;
    private Label.LabelStyle textStyleTwi ;

    private Table table2;
    private Music music;

    public Level1_Final(MyGdxGame game) {

        super(game);

        textStyleAJ = new Label.LabelStyle();
        textStyleTwi = new Label.LabelStyle();

        textStyleAJ.font = new Fonts().createFont(40, Color.ORANGE, "anime");
        textStyleTwi.font = new Fonts().createFont(40, Color.VIOLET, "anime");

        introImage = new Image();
        textDialog = new Label("", textStyleTwi);
        textDialog.setWrap(true);

        changeDialog(0);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(introImage);

        table.row();
        table.add(textDialog).expand(true, true).size(1100, 200).center();

        Image pressA = new Image();
        Image pressD = new Image();

        pressA.setDrawable(new Image(new Texture("Intros/pressA.png")).getDrawable());
        pressD.setDrawable(new Image(new Texture("Intros/pressD.png")).getDrawable());

        Label.LabelStyle choiceStyle = new Label.LabelStyle();

        choiceStyle.font = new Fonts().createFont(30, Color.WHITE, "fontinBI");

         Label choiceA = new Label("          Приободрить, сказав правду.", choiceStyle);
         Label choiceD = new Label("          Попытаться пошутить", choiceStyle);
         choiceA.setWrap(true);
         choiceD.setWrap(true);

        table2 = new Table();
        table2.setFillParent(true);
        table2.setPosition(table.getX(), table.getY());
        table2.bottom();

        table2.add().colspan(2);

        table2.row().bottom();
        table2.add(pressA).size(100, 100).bottom().padBottom(15);
        table2.add(pressD).size(100, 100).bottom().padBottom(15);

        table2.row().bottom();
        table2.add(choiceA).size(550, 100).bottom().padBottom(15).fillX();
        table2.add(choiceD).size(550, 100).bottom().padBottom(15).fillX();

        table2.setVisible(choiceWindowsView);

        stage.addActor(table);
        stage.addActor(table2);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Sound/lvl1_music.ogg"));
        music.setLooping(true);
        if (MyGdxGame.isVolumeNow()) {
            music.setVolume(MyGdxGame.getVOLUME());
            music.play();
        }
    }

    @Override
    public void show() { }

    public void changeDialog (int index) {

        switch (index) {
            case 0:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_roll.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText(".....");
                break;
            case 1:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/applejack_roll.jpg"))));
                textDialog.setStyle(textStyleAJ);
                textDialog.setText(".....");
                break;
            case 2:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_aplejack_choice.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText("Эпплджек, что мне делать ?!");
                break;
            case 3:
                beforeChoice = false;
                afterChoice = false;
                choice = true;
                textDialog.setVisible(false);
                choiceWindowsView = true;
                break;

            case 10:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/applejack_speak.jpg"))));
                textDialog.setStyle(textStyleAJ);
                textDialog.setText("Не бойся, Твайлайт! Доверься мне и разожми копыта. С тобой все будет в порядке!");
                break;
            case 11:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_believe.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText("......");
                break;
            case 12:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_fall.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText("ААААААААААААААААА");
                break;
            case 13:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_save.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText("........");
                break;
            case 14:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_smile.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText("........");
                break;

            case 20:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/applejack_speak.jpg"))));
                textDialog.setStyle(textStyleAJ);
                textDialog.setText("Прощаться с жизнью!");
                break;
            case 21:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_fall.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText("ААААААААААААААААА");
                break;
            case 22:
                introImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Intros/Level_1/twilight_save.jpg"))));
                textDialog.setStyle(textStyleTwi);
                textDialog.setText("........");
                break;

            default:
                game.setScreen(new Win(game));
                dispose();
        }

    }

    public void giveChoice () {

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
                index = 10;
                afterChoice = true;
                choice = false;
                choiceWindowsView = false;
                textDialog.setVisible(true);
                changeDialog(index);
            }

            else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                index = 20;
                afterChoice = true;
                choice = false;
                choiceWindowsView = false;
                textDialog.setVisible(true);
                changeDialog(index);
            }

    }

    public void update (float dt) {
        super.update(dt);

        table2.setVisible(choiceWindowsView);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

    @Override
    public void hide() {    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();

    }
}
