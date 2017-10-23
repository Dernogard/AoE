package com.mygdx.Sprite.Enemies.FlyEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 09.08.17.
 */
public class BeeBear extends AllFlyEnemies {

    private boolean sleep, flyDown, flyUp;

    public BeeBear(AllLevels lvl, Rectangle bounds) {
        super(lvl, bounds);

        sleep = true;
        flyDown = false;
        flyUp = false;

        defineHitBox();

        skinMonster = new TextureRegion(atlas.findRegion("BearBee"));
        setBounds(0, 0, 196 / MyGdxGame.PPM, 256 / MyGdxGame.PPM);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(skinMonster);

    }

    private void defineHitBox () {
        FixtureDef fDef2 = new FixtureDef();
        PolygonShape zone = new PolygonShape();

       zone.setAsBox(70 / MyGdxGame.PPM, 120 / MyGdxGame.PPM);

        fDef2.shape = zone;
        fDef2.filter.categoryBits = MyGdxGame.ENEMY_HIT_BIT;
        fDef2.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.SPELL_BIT;
        fDef2.isSensor = true;
        body.createFixture(fDef2).setUserData(this);
    }

    protected State getState () {
        if (isAlive) {
            if (isFly)
                return State.FLY;
            else
                return State.FALL;
        }
        else
            return State.DYE;
    }

    protected TextureRegion getFrame (float dt) {
        TextureRegion region;
        currentState = getState();

        switch (currentState) {
            case DYE:
                region = (TextureRegion) explosion.getKeyFrame(stateTimer, false);
                break;
            case FALL:
            default:
                region = skinMonster;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    @Override
    public void update(float dt) {

        super.update(dt);

        if ((body.getPosition().x - lvl.getPlayer().b2body.getPosition().x) <= 128*5/MyGdxGame.PPM && sleep) {
            sleep = false;
            flyDown = true;
        }

        if (!sleep) {
            if (flyDown) body.setLinearVelocity(-0.7f, -0.8f);
            else if (flyUp) body.setLinearVelocity(-0.7f, 0.8f);

            if (body.getPosition().y <= 0.9f + 0.1f) {
                flyDown = false;
                flyUp = true;
            }

            if (flyUp && body.getPosition().y >= 128 * 8 / MyGdxGame.PPM) {
                flyUp = false;
                body.setActive(false);
            }
        }

        setRegion(getFrame(dt));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
