package com.mygdx.Sprite.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;


public class DragonRing extends Sprite {

    protected Body body;
    protected AllLevels lvl;
    private boolean swiming, swimingDown, swimingUp;
    private Vector2 startPosition;

    public DragonRing (AllLevels lvl, Rectangle bounds) {
        this.lvl = lvl;

        swiming = (int)(Math.random()*100) % 4 == 0;

        swimingDown = true;
        swimingUp = false;

        defineDragonRingBody(bounds);

        startPosition = new Vector2(body.getPosition());

        TextureRegion texture = new TextureRegion(new Texture("resourse2/ring_sea_monster.png"), 0,0,128,128);
        setBounds(0,0,180 / MyGdxGame.PPM,128 / MyGdxGame.PPM);
        setRegion(texture);

    }

    private void defineDragonRingBody (Rectangle bounds) {
        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.KinematicBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MyGdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGdxGame.PPM);

        body = lvl.getWorld().createBody(bDef);

        shape.setAsBox((bounds.getWidth() / 1.8f) / MyGdxGame.PPM, (bounds.getHeight() / 2) / MyGdxGame.PPM);

        fDef.shape = shape;
        fDef.friction = 0.5f;

        fDef.filter.categoryBits = MyGdxGame.PLATFORM_BIT;
        fDef.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.TWILIGHT_HIT_BIT;

        body.createFixture(fDef);
    }

    public void moveFinish () {
        swiming = false;
        body.setLinearVelocity(0.3f, 0);
    }

    public void update (float dt) {
       // setRegion(texture);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y+0.02f);

        if (swiming) {
            if (body.getPosition().x - lvl.getPlayer().b2body.getPosition().x >= 128 * 8 / MyGdxGame.PPM) {
                body.setActive(false);
            }
            else body.setActive(true);
        }

            if (body.getPosition().y >= startPosition.y + 20 / MyGdxGame.PPM && swimingUp) {
                swimingUp = false;
                swimingDown = true;
            }

            if (body.getPosition().y <= startPosition.y - 160 / MyGdxGame.PPM && swimingDown) {
                swimingUp = true;
                swimingDown = false;
            }

            if (swiming && swimingDown) {
                body.setLinearVelocity(0, -0.2f);
            } else if (swiming && swimingUp) {
                body.setLinearVelocity(0, 0.2f);

            }

    }

    public void dispose () {
        lvl.getWorld().destroyBody(body);
    }

}
