package com.mygdx.Sprite.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Sprite.Hero.Spell;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 23.07.17.
 */
public abstract class Items extends Sprite {

    protected Body body;
    protected TextureRegion texture;
    protected AllLevels lvl;

    Sound usedSound;
    Fixture fixture;

    Items(AllLevels lvl, Rectangle bounds) {
        this.lvl = lvl;

        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MyGdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGdxGame.PPM);

        body = lvl.getWorld().createBody(bDef);

        shape.setAsBox((bounds.getWidth() / 5) / MyGdxGame.PPM, (bounds.getHeight() / 5) / MyGdxGame.PPM);
        fDef.shape = shape;
        fDef.isSensor = true;
        fixture = body.createFixture(fDef);
    }

    public abstract void takeItem(Twilight player);

    void setCategoryFilter (short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public void dispose () {
        usedSound.dispose();
        lvl.getWorld().destroyBody(body);
    }
}
