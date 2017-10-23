package com.mygdx.Sprite.Enemies.FlyEnemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Screen.Levels.Level_2;
import com.mygdx.Sprite.Enemies.WaterEnemies.AllWaterEnemy;
import com.mygdx.game.MyGdxGame;

public class Bat extends AllFlyEnemies {

        private Vector2 playerPosition;

        private TextureRegion fallTexture;
        private Animation flyAnimation;

        private boolean sleep, fall, flyLeft, flyUp, flyDown;

        public Bat(AllLevels lvl, Rectangle bounds) {
            super(lvl, bounds);

            sleep = true;
            fall = false;
            flyLeft = false;
            flyDown = false;
            flyUp = false;

            currentState = State.FALL;
            previousState = State.FALL;

            defineHitBox();

            fallTexture = new TextureRegion(((Level_2)lvl).getAtlasEnemy().findRegion("bat"), 0,0, 160,120);

            Array<TextureRegion> frames = new Array<>(); //Массив для временного хранения тектстур анимации
            //Заполняем массив анимацией бега Твайлайт, присваеваем его в анимацию и очищаем массив
            for (int i = 1; i < 4; i++)
                frames.add(new TextureRegion(((Level_2)lvl).getAtlasEnemy().findRegion("bat"), i * 160, 0, 160, 120));
            flyAnimation = new Animation<>(0.05f, frames);
            frames.clear();

            setBounds(0, 0, 128 / MyGdxGame.PPM, 128 / MyGdxGame.PPM);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(fallTexture);

        }

        private void defineHitBox () {
            FixtureDef fDef2 = new FixtureDef();
            CircleShape zone = new CircleShape();

            zone.setRadius(20f / MyGdxGame.PPM);

            fDef2.shape = zone;
            fDef2.filter.categoryBits = MyGdxGame.ENEMY_HIT_BIT;
            fDef2.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.SPELL_BIT;
            fDef2.isSensor = true;
            body.createFixture(fDef2).setUserData(this);
        }

    protected State getState () {
        if (isAlive) {
            if (flyLeft)
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

                case FLY:
                    region = (TextureRegion) flyAnimation.getKeyFrame(stateTimer, true);
                    break;

                case FALL:
                    default:
                    region = fallTexture;
                    break;

            }

            stateTimer = currentState == previousState ? stateTimer + dt : 0;
            previousState = currentState;


            return region;
        }

        @Override
        public void update(float dt) {

            super.update(dt);

            if ((body.getPosition().x - lvl.getPlayer().b2body.getPosition().x) <= 128*10/MyGdxGame.PPM && sleep) {
                sleep = false;
                fall = true;
                playerPosition = new Vector2(lvl.getPlayer().b2body.getPosition().x , lvl.getPlayer().b2body.getPosition().y);
                currentState = State.FLY;
            }

            if (!sleep) {

                if (fall) body.setLinearVelocity(0,-0.6f);

                if (body.getPosition().y <= 1.2f && fall) {
                    body.setLinearVelocity(0,0);
                    fall = false;
                    flyLeft = true;
                    flyUp = true;
                }

                if (flyLeft) {

                    if (body.getPosition().y >= 1.1f + 0.2f && flyUp) {
                        flyUp = false;
                        flyDown = true;
                    }

                    if (body.getPosition().y <= 1.1f - 0.2f  && flyDown) {
                        flyUp = true;
                        flyDown = false;
                    }
                }
                    if (flyUp) body.setLinearVelocity(-0.5f, 0.8f);
                    else if (flyDown) body.setLinearVelocity(-0.5f,-0.8f);


                if (flyLeft && body.getPosition().x <= playerPosition.x - 128 * 40 / MyGdxGame.PPM) {
                    //написать метод уничтожения врага, равно как и в остальные его сунуть
                    flyLeft = false;
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


