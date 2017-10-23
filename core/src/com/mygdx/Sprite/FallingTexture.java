package com.mygdx.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 25.07.17.
 */
public class FallingTexture extends Sprite {

    public Texture texture;
    public Body b2body;
    private World world;
    private AllLevels lvl;

    public FallingTexture(AllLevels lvl, Sprite sprite) {
        this.texture = sprite.getTexture();
        this.world = lvl.getWorld();

        setRotation((float)Math.random() * 20);

        defineBlock(world, sprite.getX(), sprite.getY());

        setBounds(0, 0, texture.getWidth() / MyGdxGame.PPM, texture.getHeight() / MyGdxGame.PPM );

    }

    private void defineBlock(World world, float x, float y) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(x * 128 / MyGdxGame.PPM, y * 128 / MyGdxGame.PPM);
        bDef.type = BodyDef.BodyType.KinematicBody;

        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / MyGdxGame.PPM, getHeight() / MyGdxGame.PPM);

        fDef.filter.categoryBits = MyGdxGame.NOTHING_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);
    }

    private void moveDown () {

        if (b2body.getPosition().y <= -1f ) {
            b2body.setActive(false);

        }

        b2body.setLinearVelocity(0, -0.5f);

        setPosition(b2body.getPosition().x, b2body.getPosition().y);
        setRegion(texture);
    }

    public void update() {

            moveDown();

    }

    public void dispose () {
        world.destroyBody(b2body);
    }


}
