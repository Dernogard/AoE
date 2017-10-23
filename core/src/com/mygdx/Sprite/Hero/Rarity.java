package com.mygdx.Sprite.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Screen.Intros.GameOver;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

public class Rarity extends  Sprite {

        private TextureRegion rarityStand;

        private int countPickOut;

        private Body b2body;

        private AllLevels lvl;
        private TextureAtlas ajTexture;


        public Rarity (AllLevels lvl) {

            this.lvl = lvl;

            defineRarity();

            ajTexture = new TextureAtlas(Gdx.files.internal("Atlas/Rarity.pack"));

            countPickOut = 1;

            rarityStand = ajTexture.findRegion("rarity_stand");
            rarityStand.flip(true, false);

            setBounds(0, 0, 150 / MyGdxGame.PPM, 133 / MyGdxGame.PPM );
            setRegion(rarityStand);
        }

        //Метод создает физическое тело и её "Фикстуру"
        private void defineRarity() {
            BodyDef bDef = new BodyDef();
            bDef.position.set(200 / MyGdxGame.PPM, 410 / MyGdxGame.PPM);
            bDef.type = BodyDef.BodyType.DynamicBody;

            b2body = lvl.getWorld().createBody(bDef);

            FixtureDef fDef = new FixtureDef();

            CircleShape shape = new CircleShape();
            shape.setRadius(30 / MyGdxGame.PPM);

            fDef.filter.categoryBits = MyGdxGame.APPLEJACK_BIT;
            fDef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.TWILIGHT_BIT;

            fDef.friction = 1f;
            //fDef.density = 100f;

            fDef.shape = shape;
            b2body.createFixture(fDef).setUserData(this);
        }

        public void pickOut () {
            switch (countPickOut) {

                    case 0:
                   rarityStand = ajTexture.findRegion("rarity_stand");
                        rarityStand.flip(true, false);
                   break;

                   case 1:
                    rarityStand = ajTexture.findRegion("rarity_stand_2");
                       rarityStand.flip(true, false);
                    break;

                    case 2:
                    rarityStand = ajTexture.findRegion("rarity_stand_3");
                        rarityStand.flip(true, false);
                    break;

                    case 3:
                        default:
                    rarityStand = ajTexture.findRegion("rarity_sit_fall");
                    b2body.getFixtureList().first().setFriction(0.1f);
                    break;
            }
            countPickOut++;
        }

        @Override
        public void draw(Batch batch) {
            super.draw(batch);
        }

        public void update (float dt) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y + 0.06f - getHeight() / 2);
            setRegion(rarityStand);

            //Смерть Рэрити в воде = GameOver
            if (b2body.getPosition().y <= -1) {
                lvl.getGame().setScreen(new GameOver(lvl.getGame(), lvl.getNumLvl(), true));
                lvl.dispose();
            }

        }

        public void dispose () {
            ajTexture.dispose();
            lvl.getWorld().destroyBody(b2body);

        }
    }
