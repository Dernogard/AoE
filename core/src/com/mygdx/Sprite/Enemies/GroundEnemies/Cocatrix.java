package com.mygdx.Sprite.Enemies.GroundEnemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 26.07.17.
 */
public class Cocatrix extends AllEnemies {
    public Cocatrix(AllLevels lvl, Rectangle bounds) {
        super(lvl, bounds);

        skinMonster = new TextureRegion(lvl.getAtlas().findRegion("Enemies"), 0, 0, 128, 128);
        setBounds(0, 0, 128 / MyGdxGame.PPM, 128 / MyGdxGame.PPM);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(skinMonster);

        defineHitZone();

    }

    private void defineHitZone () {
        FixtureDef fDef2 = new FixtureDef();
        PolygonShape zone = new PolygonShape();

        Vector2[] coords = new Vector2[4];

        coords[0] = new Vector2(-32,-32).scl(1 / MyGdxGame.PPM);
        coords[1] = new Vector2(32,-32).scl(1 / MyGdxGame.PPM);
        coords[2] = new Vector2(-40,80).scl(1 / MyGdxGame.PPM);
        coords[3] = new Vector2(40,80).scl(1 / MyGdxGame.PPM);

        zone.set(coords);

        fDef2.shape = zone;
        fDef2.filter.categoryBits = MyGdxGame.ENEMY_HIT_BIT;
        fDef2.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.SPELL_BIT;
        fDef2.isSensor = true;
        body.createFixture(fDef2).setUserData(this);
    }
}
