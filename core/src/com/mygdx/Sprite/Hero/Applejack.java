package com.mygdx.Sprite.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

public class Applejack extends Sprite {

    public enum State {JUMPING, STANDING, SPEAKING, RUNNING}  //"состояния"

    private TextureRegion ajJump;
    private TextureRegion ajStand;
    private TextureRegion ajSpeak;
    private Animation ajRun;

    private float stateTimer;               //Таймер хрен пойми чего, но нужен, блеать!

    private Body b2body;

    private AllLevels lvl;
    private State currentState;            //Текущее и прошлое состояния

    public boolean endLvl;                  //Флаг запуска скрипта конца уровня
    private boolean runAJ;                  //Флаг запуска бега Эпплджек

    public Applejack (AllLevels lvl) {

        this.lvl = lvl;

        defineAJ();

        TextureAtlas ajTexture = new TextureAtlas(Gdx.files.internal("Atlas/Applejack.pack"));

        currentState = State.STANDING;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<>(); //Массив для временного хранения тектстур анимации
        //Заполняем массив анимацией бега, присваеваем его в анимацию и очищаем массив
        for (int i = 1; i < 9; i++)
            frames.add(new TextureRegion(ajTexture.findRegion("AJ_run"), i * 128, 0, 128, 73));
        ajRun = new Animation<>(0.04f, frames);
        frames.clear();

        ajJump = ajTexture.findRegion("AJ_jump");
        ajStand = ajTexture.findRegion("AJ-stand");
        ajSpeak = ajTexture.findRegion("AJ-stand-with-dialog");

        setBounds(0, 0, 256 / MyGdxGame.PPM, 200 / MyGdxGame.PPM );
        setRegion(ajStand);
    }

    //Метод создает физическое тело и её "Фикстуру"
    private void defineAJ() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(130 / MyGdxGame.PPM, 347 / MyGdxGame.PPM);
        bDef.type = BodyDef.BodyType.KinematicBody;

        b2body = lvl.getWorld().createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40/ MyGdxGame.PPM, 50/ MyGdxGame.PPM);

        fDef.filter.categoryBits = MyGdxGame.APPLEJACK_BIT;
        fDef.filter.maskBits = MyGdxGame.GROUND_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);
    }

    // Метод возвращает текстуру в зависимости от состояния
    private TextureRegion getFrame (float dt) {
        TextureRegion region;

        switch(currentState) {
            case JUMPING:
                region = ajJump;
                break;
            case RUNNING:
                region = (TextureRegion) ajRun.getKeyFrame(stateTimer, true);
                break;
            case SPEAKING:
                region = ajSpeak;
                break;
            case STANDING:
            default:
                region = lvl.getGame().isDialog_1lvl_start() ? ajStand : ajSpeak;
                break;
        }

        stateTimer = stateTimer + dt;

        return region;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void update (float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y + 0.02f - getHeight() / 2);

        setRegion(getFrame(dt));

        if (runAJ) {

            if (b2body.getPosition().x <= 50048/MyGdxGame.PPM)
                    b2body.applyLinearImpulse(new Vector2(-0.005f, 0), b2body.getWorldCenter(), true);

            else
                b2body.applyLinearImpulse(new Vector2(-0.05f, 0), b2body.getWorldCenter(), true);
        }

        if (b2body.getPosition().x <= 50048/MyGdxGame.PPM && currentState == State.RUNNING)
            jumpAJ();
    }

    public void ajFallInStart () { currentState = State.SPEAKING; }

    public void redefineAJ () {
        lvl.getWorld().destroyBody(b2body);

        BodyDef bDef = new BodyDef();
        bDef.position.set(51000 / MyGdxGame.PPM, 347 / MyGdxGame.PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;

        b2body = lvl.getWorld().createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(35 / MyGdxGame.PPM);

        fDef.filter.categoryBits = MyGdxGame.APPLEJACK_BIT;
        fDef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.FINAL_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

    }

    public void runAJ () {
        currentState = State.RUNNING;
        runAJ = true;
    }

    private void jumpAJ () {
        currentState = State.JUMPING;
        b2body.applyLinearImpulse(new Vector2(0, 3.5f), b2body.getWorldCenter(), true);
    }

    public void setWin () { endLvl = true; }

    public void dispose () {
        lvl.getWorld().destroyBody(b2body);
    }
}


