package com.mygdx.Sprite.Enemies.WaterEnemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 11.08.17.
 */
public class Fish extends AllWaterEnemy {

    private Vector2 startPosition;
    private double angl;
    private boolean sleep;

    public Fish(AllLevels lvl, Rectangle bounds) {
        super(lvl, bounds);

        sleep = true;

        startPosition = new Vector2(body.getPosition());

        defineHitBox();

        skinMonster = new TextureRegion(atlas.findRegion("fish"), 0,0, 64,41);
        setBounds(0, 0, 64 / MyGdxGame.PPM, 41 / MyGdxGame.PPM);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(skinMonster);

    }

    private void defineHitBox () {
        FixtureDef fDef2 = new FixtureDef();
        CircleShape zone = new CircleShape();

        zone.setRadius(30 / MyGdxGame.PPM);

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

        if (!sleep) {
            angl += 2f*dt;
            float x = startPosition.x - 0.5f + (200 / MyGdxGame.PPM) * (float)Math.cos(angl);
            float y = startPosition.y + 0.4f + (200 / MyGdxGame.PPM) * (float)Math.sin(angl);

            body.setTransform(x, y, 0);
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
