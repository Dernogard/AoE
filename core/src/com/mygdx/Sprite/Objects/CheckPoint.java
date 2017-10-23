package com.mygdx.Sprite.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

    public class CheckPoint extends Sprite {

    public enum State {ON, OFF, ACTIVATE}  //"состояния"
    private State currentState;                                 //Текущее и прошлое состояния
    private State previousState;

    private float stateTimer;

    private Body body;

    private TextureRegion textureON;
    private TextureRegion textureOFF;
    private Animation activating;

    private AllLevels lvl;
    public Vector2 startPosition;
    private int numCheckPoint;

    public CheckPoint (AllLevels lvl, Rectangle bounds, int num) {
        this.lvl = lvl;

        currentState = State.OFF;
        previousState = State.OFF;

        numCheckPoint = num;

        defineCheckPoint(bounds);

        startPosition = new Vector2(body.getPosition());

        Texture atlas = new Texture("OtherResourse/checkpointAnimation.png");

        textureON = new TextureRegion(atlas, 0,0,18*62,100);
        textureOFF = new TextureRegion(atlas, 0,0,62,100);


        Array<TextureRegion> frames = new Array<>(); //Массив для временного хранения тектстур анимации
        //Заполняем массив анимацией бега Твайлайт, присваеваем его в анимацию и очищаем массив
        for (int i = 1; i < 19; i++)
            frames.add(new TextureRegion(atlas, i * 62, 0, 62, 100));
        activating = new Animation<>(0.05f, frames);
        frames.clear();

        setBounds(0,0,128 / MyGdxGame.PPM,200 / MyGdxGame.PPM);
        setRegion(textureOFF);

        //atlas.dispose();

    }

    private void defineCheckPoint (Rectangle bounds) {
        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.KinematicBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 5) / MyGdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGdxGame.PPM);

        body = lvl.getWorld().createBody(bDef);

        shape.setAsBox((bounds.getWidth() / 5) / MyGdxGame.PPM, (bounds.getHeight() / 2) / MyGdxGame.PPM);
        fDef.shape = shape;

        fDef.filter.categoryBits = MyGdxGame.CHECKPOINT_BIT;
        fDef.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.TWILIGHT_HIT_BIT;

        fDef.isSensor = true;

        body.createFixture(fDef).setUserData(this);

    }

    private TextureRegion getFrame(float dt) {
            TextureRegion region;

            switch (currentState) {
                case ON:
                    region = textureON;
                    break;
                case ACTIVATE:
                    region = (TextureRegion) activating.getKeyFrame(stateTimer, false);
                    break;

                case OFF:
                default:
                    region = textureOFF;
                    break;

            }

            stateTimer = currentState == previousState ? stateTimer + dt : 0;
            previousState = currentState;

            return region;
        }

    public void update (float dt) {
        setRegion(getFrame(dt));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 0.07f);
    }

    public void setCheckPoint () {
        currentState = State.ACTIVATE;
        MyGdxGame.setNumCheckPoint(this.numCheckPoint);
    }

    public void dispose () {
        lvl.getWorld().destroyBody(body);
    }

}
