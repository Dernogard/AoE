package com.mygdx.Sprite.Enemies.GroundEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 24.07.17.
 */
public class AllEnemies extends Sprite{

    protected enum State {STANDING, DYING}   //Состояния врагов
    private State currentState;
    private State previousState;

    private World world;

    protected Body body;
    private Fixture fixture;

    TextureRegion skinMonster;

    private Animation explosion;
    private float stateTimer;

    private boolean isAlive;              //Флаг живого монстра

    private boolean falling;              //Флаг для падения вместе с землей на 1 уровне
    private boolean destroyed;            //Флаг полного уничтожения врага

    private boolean giveScore;            //Флаг для разового начисления очков

    protected AllLevels lvl;

    private Sound explode;

    AllEnemies(AllLevels lvl, Rectangle bounds) {

        this.lvl = lvl;
        this.world = lvl.getWorld();

        falling = false;
        isAlive = true;

        stateTimer = 0;

        currentState = State.STANDING;
        previousState = State.STANDING;

        defineEnemy(bounds);

        Array<TextureRegion> frames = new Array<>(); //Массив для временного хранения тектстур анимации
        //Заполняем массив анимацией взрыва
        for (int i = 0; i < 12; i++)
            frames.add(new TextureRegion(lvl.getAtlas().findRegion("Explosion"), i * 96, 0, 96, 96));
        explosion = new Animation<>(0.1f, frames);
        frames.clear();

        explode = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/explode.wav"));

    }

    public void setFalling (boolean falling) {
        setRotation(15);
        this.falling = falling;
    }

    public void setAlive (boolean x) {
        isAlive = x;
    }

    private void defineEnemy (Rectangle bounds) {
        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MyGdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGdxGame.PPM);

        body = world.createBody(bDef);

        shape.setRadius(35  / MyGdxGame.PPM);
        fDef.shape = shape;

        fDef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fDef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.SPELL_BIT | MyGdxGame.TWILIGHT_BIT;

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

    private State getState () {
        if (isAlive)
            return State.STANDING;

        else
            return State.DYING;
        }

    private TextureRegion getFrame (float dt) {
        TextureRegion region;
        currentState = getState();

        switch (currentState) {
            case DYING:
                region = (TextureRegion) explosion.getKeyFrame(stateTimer, false);
                break;
            case STANDING:
            default:
                  region = skinMonster;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

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
            lvl.getWC().getEnemies().removeValue(this, true);

        }
    }

    public void update (float dt) {

        setRegion(getFrame(dt));

        if (currentState == State.DYING && !destroyed )
            killEnemy();

        if (falling && body.getPosition().y >= -1 && currentState != State.DYING) {
            setCategoryFilter(MyGdxGame.NOTHING_BIT);
            body.setLinearVelocity(0, -0.3f);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }

        if (body.getPosition().y <= -1) {
            lvl.getWorld().destroyBody(body);
            lvl.getWC().getEnemies().removeValue(this, true);
        }

    }

    public void dispose () {
        world.destroyBody(body);
        explode.dispose();
    }

}
