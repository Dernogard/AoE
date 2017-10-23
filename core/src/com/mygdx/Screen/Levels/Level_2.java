package com.mygdx.Screen.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Dialogs.Dialog_2lvl_start;
import com.mygdx.Screen.Intros.Level2_Final;
import com.mygdx.Screen.Intros.Win;
import com.mygdx.Sprite.Enemies.FlyEnemies.AllFlyEnemies;
import com.mygdx.Sprite.Enemies.WaterEnemies.AllWaterEnemy;
import com.mygdx.Sprite.Enemies.WaterEnemies.WaterWave;
import com.mygdx.Sprite.FallingTexture;
import com.mygdx.Sprite.Hero.Applejack;
import com.mygdx.Sprite.Hero.Rarity;
import com.mygdx.Sprite.Hero.Spell;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.Sprite.Objects.DragonRing;
import com.mygdx.Tools.WorldContactListener_2;
import com.mygdx.Tools.WorldCreator;
import com.mygdx.Tools.WorldCreator_2;
import com.mygdx.game.MyGdxGame;

public class Level_2 extends AllLevels {

    private WorldCreator_2 creator;
    private TextureAtlas atlasEnemy;
    private Array<WaterWave> massWave;          //Массив, хранящий волны. Особо уже не нужен, но пусть будет, раз уж сделал
    private float waveTimer;                    //Таймер до следующей волны
    private boolean startFinal, endLvl;         //Флаги начала финальной сценки и окончательной концовки уровня
    private Texture background;                 //Градиентное небо, для того, чтобы отрисовывать всё "за волнами"
    private Rarity rarity;
    private Box2DDebugRenderer b2dr;

    public Level_2(MyGdxGame game) {
        super(game);

        massWave = new Array<>();
        waveTimer = 0;

        world.setContactListener(new WorldContactListener_2());

        numLvl = 2;

        startFinal = false;
        endLvl = false;
        b2dr = new Box2DDebugRenderer();

        atlas = new TextureAtlas("Atlas/Twilight_Enemies.pack");
        atlasEnemy = new TextureAtlas(Gdx.files.internal("Atlas/EnemyRarity1.pack"));

        background = new Texture("OtherResourse/sky.png");

        map = mapLoader.load("map2-1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);

        creator = new WorldCreator_2(this);

        Vector2 startPlayerPosition = getStartPlayerPosition();
        player = new Twilight(this, new Vector2(startPlayerPosition.x - (128*3)/MyGdxGame.PPM, startPlayerPosition.y));

        rarity = new Rarity(this);

        dialogView = game.isDialog_2lvl_start();

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Sound/lvl1_music.ogg"));
        music.setLooping(true);

        pauseSound = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/smb_pause.wav"));

        if (MyGdxGame.isVolumeNow()) {
            music.setVolume(MyGdxGame.getVOLUME());
            music.play();
        }

        dialog = new Dialog_2lvl_start(this);
    }

    //Выдает стартовую позицию игрока, в зависимости от пройденного чекпоинта
    private Vector2 getStartPlayerPosition () {

        if (MyGdxGame.getNumCheckPoint() <= creator.getCheckPointArray().size)
            return creator.getCheckPointArray().get(MyGdxGame.getNumCheckPoint()).startPosition;

        else return creator.getCheckPointArray().get(0).startPosition;

    }


    @Override
    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TextureAtlas getAtlasEnemy() {
        return atlasEnemy;
    }

    @Override
    public TiledMap getMap() {
        return map;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Twilight getPlayer() {
        return player;
    }

    @Override
    public MyGdxGame getGame() {
        return game;
    }

    @Override
    public Applejack getApplejack() {
        return null;
    }

    public Array<WaterWave> getMassWave() {
        return massWave;
    }

    @Override
    public Array<FallingTexture> getMassSprite() {
        return null;
    }

    @Override
    public WorldCreator getWC() {
        return null;
    }

    @Override
    public WorldCreator_2 getWC_2() {
        return creator;
    }

    //Метод пускает волны каждые 5 секунд (время настраивается здесь же)
    private void pushWaterWave (float dt) {
        waveTimer += dt;

        if (waveTimer >= 5) {
            massWave.add(new WaterWave(this));
            waveTimer = 0;
        }
    }

    //Финальная сценка
    private void startFinal () {
        if (!startFinal) {
            startFinal = true;
            waveTimer = 0;      //Используем имеющийся таймер волн, для задержки перед сменой экрана. Заодно не даём волне шлепнуть игрока на финальном прокате
            finishingLevel();

        }

        if (endLvl) {
           // game.setScreen(new Level2_Final(game));
            MyGdxGame.setNumCheckPoint(0);
            Win.setScore(Twilight.getScore());
            game.setScreen(new Win(game));
            dispose();
        }

    }

    //Финальная сценка - проезд на кольце
    private void finishingLevel () {

        creator.getDragonRingsArray().get(creator.getDragonRingsArray().size-1).moveFinish();

    }

    @Override
    public void update(float dt) {
        if (player.getWin()) {
            startFinal();
            if (waveTimer >= 3)  endLvl = true;     //Сия конструкция говорит о том, что после обнуления таймера волн, через 3 секунды хреначим экран победы
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            {
            if (MyGdxGame.isVolumeOn())
                pauseSound.play(MyGdxGame.getVOLUME());

            pause = !pause;
            game.setDialog_2lvl_start(false);
            }

        dialogView = game.isDialog_2lvl_start();        //Флаг ставит видимость стартовой диалоговой системы

        if (!pause) {
            super.update(dt);

            handleInput(dt);

            pushWaterWave(dt);

            rarity.update(dt);

            for (int i = 0; i < creator.getFlyEnemies().size; i++) {
                AllFlyEnemies enemy = creator.getFlyEnemies().get(i);

                if (enemy != null) {
                    enemy.update(dt);
                }
            }

            for (int i = 0; i < creator.getDragonRingsArray().size; i++) {
                DragonRing rings = creator.getDragonRingsArray().get(i);

                if (rings != null) {
                    rings.update(dt);
                }
            }

            for (int i = 0; i < creator.getCheckPointArray().size; i++) {
                creator.getCheckPointArray().get(i).update(dt);
            }

             for (int i = 0; i < creator.getWaterEnemiesArray().size; i++) {
                            AllWaterEnemy enemy = creator.getWaterEnemiesArray().get(i);

                            if (enemy != null) {
                                enemy.update(dt);
                            }
                        }

            for (int i = 0; i < massWave.size; i++) {
                if (massWave.get(i).isAlive)
                    massWave.get(i).update(dt);
                else massWave.removeIndex(i);
            }

        }
        else {
            if (dialogView)
                dialog.update();

            else
                inGameMenu.update();
        }
    }

    @Override
    public void render(float delta) {

        //Gdx.gl.glClearColor(144/255f, 95/255f, 185/255f, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();

        game.batch.draw(background, 0,0, 128000/MyGdxGame.PPM, 900/MyGdxGame.PPM);

        player.draw(game.batch);

        rarity.draw(game.batch);

        for (int i = 0; i < creator.getDragonRingsArray().size ; i++)
            creator.getDragonRingsArray().get(i).draw(game.batch);


        for (int i = 0; i < creator.getFlyEnemies().size ; i++)
            creator.getFlyEnemies().get(i).draw(game.batch);

        for (int i = 0; i < creator.getItemsArray().size ; i++)
            creator.getItemsArray().get(i).draw(game.batch);

        for (int i = 0; i < creator.getWaterEnemiesArray().size; i++)
            creator.getWaterEnemiesArray().get(i).draw(game.batch);

        for (int i = 0; i < massWave.size; i++) {
            massWave.get(i).draw(game.batch);
        }

        for (int i = 0; i < creator.getCheckPointArray().size; i++) {
            creator.getCheckPointArray().get(i).draw(game.batch);
        }


        game.batch.end();

        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        //fb2dr.render(world, gamecam.combined);
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

        update(delta);      //Вызывается последним, иначе кидает ошибку

    }

    //Метод управления игроком. Потом переписать в отдельный класс
    private void handleInput (float dt) {
        if (!player.isDead() && !startFinal) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W))
                    && (player.getState() != Twilight.State.JUMPING)
                    && (player.getState() != Twilight.State.FALLING))
                player.twilightJump();

            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2 && !player.isAllTimeRun())
                player.b2body.applyLinearImpulse(new Vector2(0.08f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2 && !player.isAllTimeRun())
                player.b2body.applyLinearImpulse(new Vector2(-0.08f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.F))
                player.fireSpell(Spell.State.HORIZONT);

            if (Gdx.input.isKeyJustPressed(Input.Keys.E))
                player.fireSpell(Spell.State.DIAGONAL_UP);

        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public boolean isDialogView() {
        return super.isDialogView();
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() { }


    @Override
    public void dispose() {
        super.dispose();

        rarity.dispose();
        massWave.clear();
        background.dispose();
        creator.dispose();
        //world.dispose();  //Должен диспоузиться последним иначе каюк


    }
}
