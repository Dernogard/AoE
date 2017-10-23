package com.mygdx.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Sprite.Enemies.GroundEnemies.*;
import com.mygdx.Sprite.Items.Apples;
import com.mygdx.Sprite.Items.Health;
import com.mygdx.Sprite.Items.Items;
import com.mygdx.game.MyGdxGame;


/**
 * Created by velz on 23.07.17.
 */
public class WorldCreator {

    private Array<AllEnemies> enemiesArray;
    private Array<Items> itemsArray;

    public WorldCreator (AllLevels lvl) {

        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        FixtureDef fDef2 = new FixtureDef();
        Body body;

        enemiesArray = new Array<>();
        itemsArray = new Array<>();


        //Забираем из карты все что помечено как "Земля" и рисуем её физику
        for (MapObject object : lvl.getMap().getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = lvl.getWorld().createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getHeight() / 2) / MyGdxGame.PPM);
            fDef.shape = shape;
            fDef.filter.categoryBits = MyGdxGame.GROUND_BIT;
            fDef.filter.maskBits = MyGdxGame.ENEMY_BIT | MyGdxGame.TWILIGHT_BIT | MyGdxGame.TWILIGHT_HIT_BIT | MyGdxGame.APPLEJACK_BIT;
            body.createFixture(fDef).setUserData(this);
        }


        //Забираем из карты Камни
        for (MapObject object : lvl.getMap().getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.KinematicBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = lvl.getWorld().createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getHeight() / 2) / MyGdxGame.PPM);
            fDef.filter.categoryBits = MyGdxGame.LOCK_BIT;
            fDef.filter.maskBits = MyGdxGame.ENEMY_BIT | MyGdxGame.TWILIGHT_BIT | MyGdxGame.SPELL_BIT;
            fDef.shape = shape;
            body.createFixture(fDef).setUserData(this);

            EdgeShape danger = new EdgeShape();
            danger.set((0 - rect.getWidth() / 2) / MyGdxGame.PPM,(0 - rect.getHeight()) / MyGdxGame.PPM, (0 - rect.getWidth() / 2) / MyGdxGame.PPM,(rect.getHeight() / 3f) / MyGdxGame.PPM);
            fDef2.filter.categoryBits = MyGdxGame.LOCK_HIT_BIT;
            fDef.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.SPELL_BIT;
            fDef2.shape = danger;
            fDef2.isSensor = true;
            body.createFixture(fDef2).setUserData(this);
        }

        //Забираем из карты финальную область
        for (MapObject object : lvl.getMap().getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = lvl.getWorld().createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getHeight() / 2) / MyGdxGame.PPM);
            fDef.shape = shape;
            fDef.filter.categoryBits = MyGdxGame.FINAL_BIT;
            fDef.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.TWILIGHT_HIT_BIT | MyGdxGame.APPLEJACK_BIT;
            fDef.isSensor = true;
            body.createFixture(fDef).setUserData(this);
        }


        //Создаем яблоки

        for (MapObject object : lvl.getMap().getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            itemsArray.add(new Apples(lvl, rect));
        }

        //Создаем зелья здоровья

        for (MapObject object : lvl.getMap().getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            itemsArray.add(new Health(lvl, rect));
        }

        //Создаем врагов

        for (MapObject object : lvl.getMap().getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            int z = 1 + (int) (Math.random() * 4);
            switch (z) {
                case 1:
                    enemiesArray.add(new Cocatrix(lvl, rect));
                    break;
                case 2:
                    enemiesArray.add(new Ghost(lvl, rect));
                    break;
                case 3:
                    enemiesArray.add(new Monster(lvl, rect));
                    break;
                case 4:
                case 5:
                default:
                    enemiesArray.add(new Shake(lvl, rect));
                    break;
            }

        }

    }

    public Array<AllEnemies> getEnemies () {
        return enemiesArray;
    }

    public Array<Items> getItemsArray() {
        return itemsArray;
    }

    public void dispose () {
        for (int i = 0; i < enemiesArray.size; i++) {
            enemiesArray.get(i).dispose();
        }
        for (int i = 0; i < itemsArray.size; i++) {
            itemsArray.get(i).dispose();
        }
        enemiesArray.clear();
        itemsArray.clear();
    }
}
