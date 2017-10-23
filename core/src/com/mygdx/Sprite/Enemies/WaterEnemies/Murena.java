package com.mygdx.Sprite.Enemies.WaterEnemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 11.08.17.
 */
public class Murena extends AllWaterEnemy {

    private Vector2 startPosition;
    private boolean sleep, actionUp, actionDown;

    public Murena(AllLevels lvl, Rectangle bounds) {
        super(lvl, bounds);

        sleep = true;
        actionUp = true;
        actionDown = false;

        startPosition = new Vector2(body.getPosition());

        defineHitBox();

        skinMonster = new TextureRegion(atlas.findRegion("murena_open"), 0,0, 128,256);
        setBounds(0, 0, 128 / MyGdxGame.PPM, 256 / MyGdxGame.PPM);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(skinMonster);

    }

    private void defineHitBox () {
        FixtureDef fDef2 = new FixtureDef();
        PolygonShape zone = new PolygonShape();

        zone.setAsBox(64 / MyGdxGame.PPM, 128 / MyGdxGame.PPM);

        fDef2.shape = zone;
        fDef2.filter.categoryBits = MyGdxGame.ENEMY_HIT_BIT;
        fDef2.filter.maskBits = MyGdxGame.TWILIGHT_BIT;
        fDef2.isSensor = true;
        body.createFixture(fDef2).setUserData(this);
    }

    @Override
    protected void defineEnemy(Rectangle bounds) {
        super.defineEnemy(bounds);
    }

    @Override
    public void setAlive(boolean x) {
        super.setAlive(x);
    }

    @Override
    public void update(float dt) {
        super.update(dt);


            if (body.getPosition().x - lvl.getPlayer().b2body.getPosition().x >= 128 * 8 / MyGdxGame.PPM) {
                body.setActive(false);

            }
            else {
                sleep = false;
                body.setActive(true);
            }

        if (body.getPosition().y >= startPosition.y + 230 / MyGdxGame.PPM && actionUp) {

            actionUp = false;
            actionDown = true;
            skinMonster = new TextureRegion(atlas.findRegion("murena_close"), 0,0, 128,256);
        }

        if (body.getPosition().y <= startPosition.y - 30 / MyGdxGame.PPM && actionDown) {
            actionUp = true;
            actionDown = false;
            skinMonster = new TextureRegion(atlas.findRegion("murena_open"), 0,0, 128,256);

        }


        if (!sleep && actionDown) {
            body.setLinearVelocity(0, -0.4f);
        } else if (!sleep && actionUp) {
            body.setLinearVelocity(0, 0.4f);
        }

        setRegion(skinMonster);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

    }

    @Override
    public void movingWaterEnemy() {
        super.movingWaterEnemy();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
