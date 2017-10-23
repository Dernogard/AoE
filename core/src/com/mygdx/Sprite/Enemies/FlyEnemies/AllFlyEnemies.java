package com.mygdx.Sprite.Enemies.FlyEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Screen.Levels.Level_2;
import com.mygdx.Sprite.Enemies.GroundEnemies.AllEnemies;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 09.08.17.
 */
public abstract class  AllFlyEnemies extends Sprite{

    protected enum State {FLY, DYE, FALL}   //Состояния врагов

    State currentState;
    State previousState;

    protected AllLevels lvl;
    private World world;
    protected Body body;
    protected Fixture fixture;

    protected TextureAtlas atlas;
    protected TextureRegion skinMonster;

    Animation explosion;

    protected float stateTimer;

    boolean isAlive;              //Флаг живого монстра
    boolean isFly;                  //Флаг статуса полёта

    private boolean destroyed;            //Флаг полного уничтожения врага

    private boolean giveScore;            //Флаг для разового начисления очков

    private Sound explode;

    AllFlyEnemies (AllLevels lvl,  Rectangle bounds) {
        this.lvl = lvl;
        this.world = lvl.getWorld();

        currentState = State.FALL;
        previousState = State.FALL ;

        isAlive = true;
        stateTimer = 0;

        atlas = ((Level_2)lvl).getAtlasEnemy();

        defineEnemy(bounds);

        Array<TextureRegion> frames = new Array<>(); //Массив для временного хранения тектстур анимации
        //Заполняем массив анимацией взрыва
        for (int i = 0; i < 12; i++)
            frames.add(new TextureRegion(lvl.getAtlas().findRegion("Explosion"), i * 96, 0, 96, 96));
        explosion = new Animation<>(0.1f, frames);
        frames.clear();

        explode = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/explode.wav"));

    }

    private void defineEnemy (Rectangle bounds) {

        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bDef.type = BodyDef.BodyType.KinematicBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MyGdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGdxGame.PPM);

        body = world.createBody(bDef);

        shape.setRadius(20 / MyGdxGame.PPM);
        fDef.shape = shape;

        fDef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fDef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.SPELL_BIT | MyGdxGame.TWILIGHT_BIT;
        fDef.isSensor = true;
        fixture = body.createFixture(fDef);

        fixture.setUserData(this);
    }

    private void setCategoryFilter (short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        for (int i = 0; i < body.getFixtureList().size; i++) {
            body.getFixtureList().get(i).setSensor(true);
            body.getFixtureList().get(i).setFilterData(filter);
            fixture.setFilterData(filter);
        }
    }

    protected abstract State getState () ;

    protected abstract TextureRegion getFrame (float dt) ;

    private void killEnemy () {

        if (!giveScore) {
            if (MyGdxGame.isVolumeOn())
                explode.play(MyGdxGame.getVOLUME());

            Twilight.setScore(50);
            giveScore = true;
        }

        setCategoryFilter(MyGdxGame.NOTHING_BIT);

        if (stateTimer >= 1) {
            this.setAlpha(0);
            destroyed = true;
            lvl.getWorld().destroyBody(body);
            lvl.getWC_2().getFlyEnemies().removeValue(this, true);
        }
    }

    public void setAlive (boolean x) {
        isAlive = x;
    }

    public void update (float dt) {

        if (currentState == State.DYE && !destroyed )
            killEnemy();

    }

    public void dispose () {
        world.destroyBody(body);
        explode.dispose();
        atlas.dispose();

    }
}
