package com.mygdx.Sprite.Enemies.WaterEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Screen.Levels.Level_2;
import com.mygdx.Sprite.Enemies.FlyEnemies.AllFlyEnemies;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 11.08.17.
 */
public class AllWaterEnemy extends Sprite {

        protected enum State {DYE, ACTION}   //Состояния врагов
        private State currentState;
        private State previousState;

        protected AllLevels lvl;
        private World world;
        protected Body body;
        protected Fixture fixture;

        protected static TextureAtlas atlas;
        protected TextureRegion skinMonster;

        protected float stateTimer;

        private boolean isAlive;              //Флаг живого монстра


        AllWaterEnemy (AllLevels lvl,  Rectangle bounds) {
            this.lvl = lvl;
            this.world = lvl.getWorld();

            currentState = State.ACTION;
            previousState = State.ACTION ;

            isAlive = true;
            stateTimer = 0;

            atlas = ((Level_2)lvl).getAtlasEnemy();

            defineEnemy(bounds);

        }

        protected void defineEnemy (Rectangle bounds) {

            BodyDef bDef = new BodyDef();
            FixtureDef fDef = new FixtureDef();
            CircleShape shape = new CircleShape();

            bDef.type = BodyDef.BodyType.KinematicBody;
            bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MyGdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bDef);

            shape.setRadius(5 / MyGdxGame.PPM);
            fDef.shape = shape;

            fDef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
            fDef.filter.maskBits = MyGdxGame.SPELL_BIT | MyGdxGame.TWILIGHT_BIT;
            fDef.isSensor = true;
            fixture = body.createFixture(fDef);

            fixture.setUserData(this);
        }

        private State getState () {
            if (isAlive) {
                return State.ACTION;
            }
            else
                return State.DYE;
        }

        private TextureRegion getFrame (float dt) {
            TextureRegion region;
            currentState = getState();

            switch (currentState) {
                case ACTION:
                default:
                    region = skinMonster;
            }

            stateTimer = currentState == previousState ? stateTimer + dt : 0;
            previousState = currentState;

            return region;
        }

        public void setAlive (boolean x) {
            isAlive = x;
        }

        public void update (float dt) {

            setRegion(getFrame(dt));

            if (currentState == State.ACTION) {
                movingWaterEnemy();
            }
        }

        public void movingWaterEnemy() {}

    public static TextureAtlas getAtlas() {
        return atlas;
    }

    public void dispose () {
            world.destroyBody(body);
            atlas.dispose();
        }
    }



