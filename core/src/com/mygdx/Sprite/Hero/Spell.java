package com.mygdx.Sprite.Hero;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 24.07.17.
 */
public class Spell extends Sprite {

    public enum State {HORIZONT, UP, DIAGONAL_UP }

    private State thisSpellState;

    private TextureRegion spellTexture;
    private Vector2 velocity;
    private Vector2 startPosition;
    private Vector2 endPosition;

    private World world;
    private Body body;

    private Twilight player;

    private boolean isActive;
    private boolean isLeft;


    public Spell (AllLevels lvl, float x, float y, State howSpellFly) {

        this.world = lvl.getWorld();
        this.player = lvl.getPlayer();

        thisSpellState = howSpellFly;

        isActive = true;
        isLeft = !player.isRunRight();

        spellTexture = new TextureRegion(lvl.getAtlas().findRegion("spell"), 1,1,30,14);

        //Направление скорости заклинания зависит от стороны, в которую смотрит Твайлайт
        if (player.isRunRight())
            velocity = new Vector2(3f, 0);
        else
            velocity = new Vector2(-3f, 0);

        //Стартовая позиция выстрела зависит от того, какой тип анимации(текстуры) в момент выстрела. Поскольку при беге рог располагается ниже
        if (player.getState() == Twilight.State.RUNNING) {
            startPosition = new Vector2(x + 70 / MyGdxGame.PPM, y + 35 / MyGdxGame.PPM);
        } else {
            startPosition = new Vector2(x + 70 / MyGdxGame.PPM, y + 80 / MyGdxGame.PPM);
        }

        //Когда бежим (и стреляем) влево, нужно сместить координату х
        if (!player.isRunRight()) startPosition.add(-130/MyGdxGame.PPM, 0);

        endPosition = player.isRunRight() ? new Vector2(startPosition.x+3f, startPosition.y) : new Vector2(startPosition.x-3f, startPosition.y);

        defineSpell(); //Создаем заклинание

        setBounds(0, 0, 60 / MyGdxGame.PPM, 28 / MyGdxGame.PPM );
        setRegion(spellTexture);

    }

    private void defineSpell () {
        BodyDef bDef = new BodyDef();
        bDef.position.set(startPosition);
        bDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(15 / MyGdxGame.PPM);

        fDef.filter.categoryBits = MyGdxGame.SPELL_BIT;
        fDef.filter.maskBits = MyGdxGame.ENEMY_HIT_BIT | MyGdxGame.LOCK_BIT | MyGdxGame.LOCK_HIT_BIT;
        fDef.isSensor = true;
        fDef.shape = shape;

        body.createFixture(fDef).setUserData(this);
        body.setGravityScale(0);

    }

    private void moveSpell (float dt) {

        switch (thisSpellState) {
            case HORIZONT:
            default:
                body.setLinearVelocity(velocity.x + dt, velocity.y);
                break;
            case DIAGONAL_UP:
                setRotation(isLeft ? 135 : 45);
                body.setLinearVelocity(velocity.x + dt, Math.abs(velocity.x) + dt);
                break;
        }

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(spellTexture);


            if (body.getPosition().x > endPosition.x && !isLeft) isActive = false;
            if (body.getPosition().x < endPosition.x && isLeft) isActive = false;
    }

    private void killSpell () {
                player.massSpell.removeValue(this, true);
                world.destroyBody(body);
    }

    public void setActive (boolean x) {
        this.isActive = x;
    }

    public void update (float dt) {

        if (this.isActive)
                moveSpell(dt);

        else killSpell();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void dispose () {

    }
}
