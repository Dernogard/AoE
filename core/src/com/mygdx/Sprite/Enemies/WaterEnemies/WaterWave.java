package com.mygdx.Sprite.Enemies.WaterEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

public class WaterWave extends Sprite {

    private Body body;
    private AllLevels lvl;
    public boolean isAlive;

    public WaterWave (AllLevels lvl) {
        this.lvl = lvl;

        isAlive = true;

        defineWaterWave();

        TextureRegion texture = new TextureRegion(AllWaterEnemy.getAtlas().findRegion("water_wave"), 0,0,183,150);
        setBounds(0,0,183 / MyGdxGame.PPM,150 / MyGdxGame.PPM);
        setRegion(texture);
    }

    private void defineWaterWave () {
        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(lvl.getPlayer().b2body.getPosition().x + (128*10) / MyGdxGame.PPM, 128 * 2.8f / MyGdxGame.PPM);

        body = lvl.getWorld().createBody(bDef);

        shape.setAsBox(40 / MyGdxGame.PPM, 64 / MyGdxGame.PPM);

        fDef.shape = shape;

        fDef.filter.categoryBits = MyGdxGame.ENEMY_HIT_BIT;
        fDef.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.GROUND_BIT;

        fDef.isSensor = true;

        body.createFixture(fDef).setUserData(this);
        body.setGravityScale(0f);
    }

    public void killEnemy () {
        setCategoryFilter(MyGdxGame.NOTHING_BIT);
        setAlpha(0);
    }

    private void setCategoryFilter (short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        body.getFixtureList().first().setFilterData(filter);

    }

    public void update (float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setLinearVelocity(-0.9f, 0f);

        if (lvl.getPlayer().b2body.getPosition().x - body.getPosition().x >= (128 * 15)/MyGdxGame.PPM ) {
            isAlive = false;
            body.setActive(false);
            lvl.getWorld().destroyBody(body);
            lvl.getMassWave().removeValue(this, true);
        }

    }

    public void dispose () {
        lvl.getWorld().destroyBody(body);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
