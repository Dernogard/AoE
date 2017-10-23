package com.mygdx.Sprite.Hero;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Screen.Levels.Level_1;
import com.mygdx.game.MyGdxGame;

/**
 * Created by nuser on 19.07.17.
 */
public class Twilight extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, DYEING}  //"состояния" героя

    private State currentState;                                 //Текущее и прошлое состояния
    private State previousState;

    private TextureRegion twilightJump;         //Текстурки различных состояний игрока
    private TextureRegion twilightStand;
    private Animation twilightRun;

    private boolean runRight;               //Флаг направления бега
    private float stateTimer;               //Таймер хрен пойми чего, но нужен, блеать!

    private boolean dead;                   //Флаг смерти игрока
    private boolean herax;                  //Флаг удара по игроку
    private boolean win;                    //Флаг победы на уровне

    private boolean onPlatform;             //Флаг проверки стояния игрока на движущейся платформе

    public Body b2body;

    private boolean allTimeRun;             //Флаг проверки постоянного бега на первом уровне для падающих блоков
    private boolean isLevel_1;              //Флаг для установки постоянного бега на 1 уровне

    private AllLevels lvl;

    private static int lives;                      // Жизни Твайлайт внутри уровня
    private static int score;                       //Очки игрока
    private float timerHit;                         //Таймер для мигания при ударе

    Array<Spell> massSpell;              //Массив заклинаний

    private AssetManager manager;                //Менеджер звуков

    private Vector2 startPosition;

    //Конструктор класса Твайлайт
    public Twilight(AllLevels lvl, Vector2 position) {

        this.lvl = lvl;
        startPosition = position;

        defineTwilight();

        massSpell = new Array<>();

        manager = new AssetManager();
        manager.load("Audio/Sound/smb_fireball.wav", Sound.class);
        manager.load("Audio/Sound/smb_jump-super.wav", Sound.class);
        manager.load("Audio/Sound/smb_mariodie.wav", Sound.class);
        manager.load("Audio/Sound/auch.wav", Sound.class);

        manager.finishLoading();

        TextureRegion twiTexture = lvl.getAtlas().findRegion("Twilight");

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runRight = false;

        score = 0;
        lives = 3;

        isLevel_1 = lvl.getClass() == Level_1.class;  //Если запущен 1 уровень = true

        allTimeRun = false; //Флаг вечного бега для падающих блоков

        Array<TextureRegion> frames = new Array<>(); //Массив для временного хранения тектстур анимации

        //Заполняем массив анимацией бега Твайлайт, присваеваем его в анимацию и очищаем массив
        for (int i = 1; i < 9; i++)
            frames.add(new TextureRegion(twiTexture, i * 128, 0, 128, 69));
        twilightRun = new Animation<>(0.05f, frames);
        frames.clear();

        twilightJump = new TextureRegion(twiTexture, 15, 0, 97, 69);

        twilightStand = new TextureRegion(twiTexture, 1170, 0, 85, 69);

        setBounds(0, 0, 170 / MyGdxGame.PPM, 140 / MyGdxGame.PPM);
        setRegion(twilightStand);
    }

    //Метод создает физическое тело Твайлайт и её "Фикстуру"
    private void defineTwilight() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(startPosition);
         //bDef.position.set(37504 / MyGdxGame.PPM, 605 / MyGdxGame.PPM);   //Временная для проверки финала, а так верная верхняя
        bDef.type = BodyDef.BodyType.DynamicBody;

        b2body = lvl.getWorld().createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        //Туловище Твайлайт - прямоугольник
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40 / MyGdxGame.PPM, 50 / MyGdxGame.PPM);

        fDef.filter.categoryBits = MyGdxGame.TWILIGHT_BIT;
        fDef.filter.maskBits = MyGdxGame.APPLE_BIT | MyGdxGame.LOCK_HIT_BIT | MyGdxGame.GROUND_BIT | MyGdxGame.LOCK_BIT | MyGdxGame.ENEMY_HIT_BIT | MyGdxGame.PLATFORM_BIT | MyGdxGame.CHECKPOINT_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);
    }

    // Метод возвращает текстуру в зависимости от состояния Твайлайт
    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        currentState = getState();

        switch (currentState) {
            case JUMPING:
                region = twilightJump;
                break;

            case RUNNING:
                region = (TextureRegion) twilightRun.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
            default:
                region = twilightStand;
                break;
        }

        //Отражаем текстуру по горизонтали, в зависимости от направления движения Твайлайт
        if ((b2body.getLinearVelocity().x < 0 || !runRight) && region.isFlipX()) {
            region.flip(true, false);
            runRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runRight) && !region.isFlipX()) {
            region.flip(true, false);
            runRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    //Метод возвращает состояние Твайлайт
    public State getState() {
        if (dead) return State.DYEING;

        else if (onPlatform) {
            if (b2body.getLinearVelocity().x != 0 && !win) return State.RUNNING;
            else return State.STANDING;
        }

        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
             return State.JUMPING;
        }
        else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        else if (b2body.getLinearVelocity().x != 0) return State.RUNNING;
        else return State.STANDING;
    }

    //Метод "пускает" заклинание
    public void fireSpell(Spell.State howSpellFly) {
        if (MyGdxGame.isVolumeOn())
            manager.get("Audio/Sound/smb_fireball.wav", Sound.class).play(MyGdxGame.getVOLUME());

        massSpell.add(new Spell(lvl, b2body.getPosition().x, b2body.getPosition().y, howSpellFly));
    }

    //Метод отрисовки, включает в себя отрисовку заклинаний
    @Override
    public void draw(Batch batch) {
        super.draw(batch);

        for (int i = 0; i < massSpell.size; i++)
            massSpell.get(i).draw(batch);
    }

    //Номер ячейки на уровне которой находится Твайлайт
    public int getTwiCell() {
        int posIndex;
        posIndex = (int) (b2body.getPosition().x * MyGdxGame.PPM) / 128;

        return posIndex;
    }

    //Метод всегда бежать, для первого уровня
    private void allTimeRun() {
        if (this.b2body.getLinearVelocity().x <= 2 && !dead)
            this.b2body.applyLinearImpulse(new Vector2(0.1f, 0), this.b2body.getWorldCenter(), true);

        allTimeRun = true;
    }

    boolean isRunRight() {return runRight; }

    public boolean isAllTimeRun() { return allTimeRun; }

    public static int getLives() { return lives; }

    public static int getScore() { return score; }

    public void setWin() {  this.win = true; }

    public boolean getWin() {   return win;  }

    public boolean isDead() {   return dead;  }

    public void setFlagPlatform (boolean x) {onPlatform = x;}

    public boolean getFlagPlatform () {return  onPlatform;}

    public void addLives() {
        if (lives < 3) Twilight.lives++;
    }

    //Метод проверяет количество жизней и либо вычитает либо ставит флаг Dead = true
    private void delLives() {
        if (lives > 1) Twilight.lives--;
        else dead = true;
    }

    public static void setScore(int score) { Twilight.score += score; }

    //Метод для смены фикстуры Твайлайт после удара
    //требуется для прохода сквозь препятствия
    private void setCategoryFilter(boolean hit) {
        Filter filter = new Filter();
        filter.categoryBits = hit ? MyGdxGame.TWILIGHT_HIT_BIT : MyGdxGame.TWILIGHT_BIT;

        for (int i = 0; i < b2body.getFixtureList().size; i++)
            b2body.getFixtureList().get(i).setFilterData(filter);

    }

    //Метод срабатывает, когда происходит удар о камень или врага
    public void hitTwilight() {
        if (!herax) {
            if (MyGdxGame.isVolumeOn())
                manager.get("Audio/Sound/auch.wav", Sound.class).play(MyGdxGame.getVOLUME());

            delLives();
            herax = true;
        }
    }

    //Метод мигания при ударе
    private void migMigTwilight() {
        if ((int) (timerHit * 10) % 2 == 0) setAlpha(0.1f);
        else setAlpha(0.8f);
    }

    //Метод обработки удара по Твайлайт
    private void hittering(float dt) {
        if (herax) {  //Если произошел удар, то ставим фикстуру пустоты и мигаем - всё это в течении примерно 3 секунд
            timerHit += dt;
            setCategoryFilter(true);
            migMigTwilight();

            if (timerHit >= 1.1f) {
                herax = false;
                setAlpha(1f);
            }
        } else {      //Возвращаем как было после timerHit секунд неуязвимости
            setCategoryFilter(false);
            timerHit = 0;
        }
    }

    public void twilightJump () {
        if (MyGdxGame.isVolumeOn())
            manager.get("Audio/Sound/smb_jump-super.wav", Sound.class).play(MyGdxGame.getVOLUME());

        b2body.applyLinearImpulse(new Vector2(0, 3.5f), b2body.getWorldCenter(), true);
    }

    public void update(float dt) {

        if (lvl.getNumLvl() == 2) {
            if (b2body.getPosition().y <= 0.6f && !win) dead = true; //Смерть в воде для 2 уровня
        }

        if (b2body.getPosition().y <= -1 && !win) dead = true;

        //Не даём Твайлайт уйти влево за экран (убрано ради пасхалки с Рэрити)
        // if(b2body.getPosition().x <= 0) b2body.setLinearVelocity(2f, 0);

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //Какой-то костыль, для верного отражения картинки Твайлайт
        if (lvl.stateTimer > 15 || !lvl.isDialogView())
            setRegion(getFrame(dt));

        if (!dead) {
            hittering(dt); // Метод обработки удара

            for (int i = 0; i < massSpell.size; i++)
                massSpell.get(i).update(dt);

            //На первом уровне всегда бежим
            if (isLevel_1)
                allTimeRun();

        }

    }

    public void dispose() {
        lvl.getWorld().destroyBody(b2body);
        massSpell.clear();
        manager.dispose();
    }
}
