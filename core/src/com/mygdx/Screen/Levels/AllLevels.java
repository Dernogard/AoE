package com.mygdx.Screen.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Screen.Dialogs.AllDialog;
import com.mygdx.Screen.Dialogs.Dialog_1lvl_start;
import com.mygdx.Screen.Interface.Hud;
import com.mygdx.Screen.Intros.GameOver;
import com.mygdx.Screen.Intros.Win;
import com.mygdx.Sprite.Enemies.WaterEnemies.WaterWave;
import com.mygdx.Sprite.FallingTexture;
import com.mygdx.Sprite.Hero.Applejack;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.Tools.Fonts;
import com.mygdx.Tools.WorldContactListener;
import com.mygdx.Tools.WorldCreator;
import com.mygdx.Tools.WorldCreator_2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.menu.IngameMenu;

/**
 * Created by velz on 24.07.17.
 */
public abstract class AllLevels implements Screen {
    //Всякие общие переменные для мира игры
    //------------------------------------------------------------------
    protected MyGdxGame game;
    OrthographicCamera gamecam;
    private Viewport gameport;

    TmxMapLoader mapLoader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;

    World world;
    private Box2DDebugRenderer b2dr;

    // ------------------------------------------------------------------

    Twilight player;
    TextureAtlas atlas;

    public boolean pause;

    Hud hud;

    Music music;
    Sound pauseSound;

    IngameMenu inGameMenu;
    AllDialog dialog;

    public float stateTimer;

    boolean dialogView;

    int numLvl; //Номер уровня, для некоторых функций

    public AllLevels (MyGdxGame game) {
        this.game = game;

        pause = false;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        b2dr = new Box2DDebugRenderer();
        world = new World(new Vector2(0,-10), true);

        inGameMenu = new IngameMenu(this);

    }

    public abstract TextureAtlas getAtlas ();
    public abstract TiledMap getMap ();
    public abstract World getWorld ();
    public abstract Twilight getPlayer ();
    public MyGdxGame getGame() {return game;}
    public abstract Applejack getApplejack() ;
    public abstract Array<WaterWave> getMassWave();
    public abstract WorldCreator getWC();
    public abstract WorldCreator_2 getWC_2();
    public abstract Array<FallingTexture> getMassSprite();

    public int getNumLvl () {
        return numLvl;
    }

    public void update (float dt) {

        if (!player.isDead()) {

            player.update(dt);
            hud.update();

            world.step(1 / 60f, 6, 2);

            //Камера следит за Твайлайт, только если она в определенных рамках и жива. 49500
            if (numLvl == 1) {
                if (!player.isDead() && player.b2body.getPosition().x >= MyGdxGame.V_WIDTH / (MyGdxGame.PPM * 2) && player.b2body.getPosition().x <= 49500 / MyGdxGame.PPM) {
                    gamecam.position.x = player.b2body.getPosition().x;
                }
            }
            else if (numLvl == 2) {
                if (!player.isDead() && player.b2body.getPosition().x >= MyGdxGame.V_WIDTH / (MyGdxGame.PPM * 2) && player.b2body.getPosition().x <= 37600 / MyGdxGame.PPM) {
                    gamecam.position.x = player.b2body.getPosition().x;
                }
            }
            else {
                if (!player.isDead() && player.b2body.getPosition().x >= MyGdxGame.V_WIDTH / (MyGdxGame.PPM * 2) ) {
                    gamecam.position.x = player.b2body.getPosition().x;
                }
            }

            gamecam.update();
            renderer.setView(gamecam);

        } else {
            game.setScreen(new GameOver(game, numLvl, false));
            dispose();

        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

         //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        game.batch.setProjectionMatrix(gamecam.combined);

    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
        inGameMenu.resize(width, height);

    }

    public boolean isDialogView () {return dialogView;}

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        hud.dispose();
        player.dispose();
        atlas.dispose();
        music.dispose();
        pauseSound.dispose();
        inGameMenu.dispose();
        dialog.dispose();
    }
}
