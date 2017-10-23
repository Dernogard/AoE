package com.mygdx.Screen.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Dialogs.Dialog_1lvl_start;
import com.mygdx.Screen.Intros.Level1_Final;
import com.mygdx.Screen.Intros.Win;
import com.mygdx.Sprite.Enemies.GroundEnemies.AllEnemies;
import com.mygdx.Sprite.Enemies.WaterEnemies.WaterWave;
import com.mygdx.Sprite.FallingTexture;
import com.mygdx.Sprite.Hero.Applejack;
import com.mygdx.Sprite.Hero.Spell;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.Tools.WorldContactListener;
import com.mygdx.Tools.WorldCreator;
import com.mygdx.Tools.WorldCreator_2;
import com.mygdx.game.MyGdxGame;

/**
 * Created by nuser on 19.07.17.
 */
public class Level_1 extends AllLevels {

    private Array<FallingTexture> massSprite;   //Массив из падающих блоков
    private Applejack applejack;
    private boolean startFinal;
    private WorldCreator creator;


    public Level_1 (MyGdxGame game) {

        super(game);

        world.setContactListener(new WorldContactListener());

        numLvl = 1;

        atlas = new TextureAtlas("Atlas/Twilight_Enemies.pack");

        player = new Twilight(this, new Vector2(128 * 3.5f / MyGdxGame.PPM, 305 / MyGdxGame.PPM));

        map = mapLoader.load("map1-1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);

        creator = new WorldCreator(this);

        dialogView = game.isDialog_1lvl_start();

        applejack = new Applejack(this);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Sound/lvl1_music.ogg"));
        music.setLooping(true);

        pauseSound = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/smb_pause.wav"));

        massSprite = new Array<>();

        if (MyGdxGame.isVolumeNow()) {
            music.setVolume(MyGdxGame.getVOLUME());
            music.play();
        }


            dialog = new Dialog_1lvl_start(this);

    }

    @Override
    public void render(float delta) {

        super.render(delta);

        game.batch.begin();

            player.draw(game.batch);

            applejack.draw(game.batch);

            for (int i = 0; i < massSprite.size; i++) {
                    if (massSprite.get(i).b2body.isActive() && massSprite.get(i) != null)
                        massSprite.get(i).draw(game.batch);
            }

            for (int i = 0; i < creator.getEnemies().size ; i++)
                creator.getEnemies().get(i).draw(game.batch);


            for (int i = 0; i < creator.getItemsArray().size ; i++)
                creator.getItemsArray().get(i).draw(game.batch);

        game.batch.end();

        if (stateTimer < 0.1) {
            stateTimer += delta;
        }
        else if (stateTimer < 10 && stateTimer > 0.1f && dialogView) {
            pause = true;
            stateTimer += 20;
        }

        if (pause) {
            if (dialogView) {
                game.batch.setProjectionMatrix(dialog.stage.getCamera().combined);
                dialog.stage.draw();
            } else {
                game.batch.setProjectionMatrix(inGameMenu.stage.getCamera().combined);
                inGameMenu.stage.draw();
            }
        }

        update(delta);


    }

    public boolean isDialogView () {return dialogView;}

    //Метод управления игроком. Потом переписать в отдельный класс
    private void handleInput (float dt) {
        if (!player.isDead()) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)) &&
                                                                (player.getState() != Twilight.State.JUMPING) &&
                                                                                                                (player.getState() != Twilight.State.FALLING))
                player.twilightJump();

            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2 && !player.isAllTimeRun())
                player.b2body.applyLinearImpulse(new Vector2(0.08f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2 && !player.isAllTimeRun())
                player.b2body.applyLinearImpulse(new Vector2(-0.08f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.F))
                player.fireSpell(Spell.State.HORIZONT);
        }
    }

    //Метод обрушивает блоки
    private void fallBlock (float dt) {

        if (player.isAllTimeRun() && player.getTwiCell() > 3 && player.getTwiCell() < 387) {  //Блоки обрушиваются только при "всегда бежит" и начиная со второго блока игры (хз почему 1 не падает)
            TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);

            for (int i = 0; i <=6; i++) {
                try {
                    int currentX = player.getTwiCell() - 3;

                    Sprite sprite = new Sprite(layer.getCell(currentX, i).getTile().getTextureRegion().getTexture());

                    sprite.setPosition(currentX, i);

                    massSprite.add(new FallingTexture(this, sprite));

                    layer.getCell(currentX, i).setTile(null);

                } catch (Exception e) {
                }
            }
        }

    }

    public void update (float dt) {

        if (player.getWin())
            startFinal();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            if (MyGdxGame.isVolumeOn())
                pauseSound.play(MyGdxGame.getVOLUME());


            pause = !pause;
            game.setDialog_1lvl_start(false);

        }

        dialogView = game.isDialog_1lvl_start();

        if (!pause) {
            super.update(dt);

            applejack.update(dt);

            handleInput(dt);

            fallBlock(dt);

            for (int i = 0; i < massSprite.size; i++)
                if (massSprite.get(i) != null && massSprite.get(i).b2body.isActive())
                    massSprite.get(i).update();
                else {
                    world.destroyBody(massSprite.get(i).b2body);
                    massSprite.removeValue(massSprite.get(i), true);
                }


            for (int i = 0; i < creator.getEnemies().size; i++) {
                AllEnemies enemy = creator.getEnemies().get(i);

                if (enemy != null) {
                    if (enemy.getX() <= player.b2body.getPosition().x - (3 * 128 / MyGdxGame.PPM))
                        enemy.setFalling(true);
                    enemy.update(dt);
                }
            }

        }
        else {
            if (dialogView)
                dialog.update();

            else
                inGameMenu.update();
        }
    }

    public TextureAtlas getAtlas () {
        return atlas;
    }

    public TiledMap getMap () {
        return map;
    }

    public Array<FallingTexture> getMassSprite() {
        return massSprite;
    }

    public World getWorld () {
        return world;
    }

    public Twilight getPlayer () {
        return player;
    }

    public Applejack getApplejack() {
        return applejack;
    }

    @Override
    public WorldCreator getWC() {
        return creator;
    }

    @Override
    public WorldCreator_2 getWC_2() {
        return null;
    }

    @Override
    public Array<WaterWave> getMassWave() {
        return null;
    }

    private void startFinal () {
        if (!startFinal) {
            startFinal = true;
            applejack.redefineAJ();
            applejack.runAJ();
        }

        if (applejack.endLvl) {
            game.setScreen(new Level1_Final(game));
            Win.setScore(Twilight.getScore());
            dispose();
        }

    }

    @Override
    public void resume() { }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}
    @Override
    public void dispose() {
        for (int i = 0; i < massSprite.size; i++) {
            massSprite.get(i).dispose();
        }
        massSprite.clear();
        applejack.dispose();


        super.dispose();
        creator.dispose();
        //world.dispose();  //Должен диспоузиться последним иначе каюк




    }

}
