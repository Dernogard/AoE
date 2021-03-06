package com.mygdx.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Sprite.Enemies.GroundEnemies.AllEnemies;
import com.mygdx.Sprite.Hero.Applejack;
import com.mygdx.Sprite.Hero.Spell;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.Sprite.Items.Items;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 23.07.17.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            case MyGdxGame.SPELL_BIT | MyGdxGame.LOCK_BIT:
            case MyGdxGame.SPELL_BIT | MyGdxGame.LOCK_HIT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.SPELL_BIT)
                    ((Spell)fixA.getUserData()).setActive(false);
                else
                    ((Spell)fixB.getUserData()).setActive(false);
                break;

            case MyGdxGame.LOCK_HIT_BIT | MyGdxGame.TWILIGHT_BIT :
                if (fixA.getFilterData().categoryBits == MyGdxGame.TWILIGHT_BIT)
                    ((Twilight)fixA.getUserData()).hitTwilight();
                else
                    ((Twilight)fixB.getUserData()).hitTwilight();
                break;

            case MyGdxGame.TWILIGHT_BIT | MyGdxGame.APPLE_BIT:
            case MyGdxGame.TWILIGHT_HIT_BIT | MyGdxGame.APPLE_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.TWILIGHT_BIT || fixA.getFilterData().categoryBits == MyGdxGame.TWILIGHT_HIT_BIT)
                    ((Items)fixB.getUserData()).takeItem((Twilight)fixA.getUserData());
                else
                    ((Items)fixA.getUserData()).takeItem((Twilight)fixB.getUserData());
                break;

            case  MyGdxGame.TWILIGHT_BIT | MyGdxGame.ENEMY_HIT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.TWILIGHT_BIT)
                    ((Twilight)fixA.getUserData()).hitTwilight();
                else
                    ((Twilight)fixB.getUserData()).hitTwilight();
                break;

            case MyGdxGame.SPELL_BIT | MyGdxGame.ENEMY_HIT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.SPELL_BIT) {
                    ((Spell)fixA.getUserData()).setActive(false);
                    ((AllEnemies)fixB.getUserData()).setAlive(false);
                }
                else {
                    ((Spell)fixB.getUserData()).setActive(false);
                    ((AllEnemies)fixA.getUserData()).setAlive(false);
                }
                break;

            case MyGdxGame.FINAL_BIT | MyGdxGame.TWILIGHT_BIT:
            case MyGdxGame.FINAL_BIT | MyGdxGame.TWILIGHT_HIT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.TWILIGHT_BIT || fixA.getFilterData().categoryBits == MyGdxGame.TWILIGHT_HIT_BIT)
                    ((Twilight)fixA.getUserData()).setWin();
                else
                    ((Twilight)fixB.getUserData()).setWin();
                break;

            case MyGdxGame.FINAL_BIT | MyGdxGame.APPLEJACK_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.APPLEJACK_BIT)
                    ((Applejack)fixA.getUserData()).setWin();
                else
                    ((Applejack)fixB.getUserData()).setWin();
                break;

        }

    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) { }
}
