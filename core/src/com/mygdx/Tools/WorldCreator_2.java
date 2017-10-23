package com.mygdx.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Sprite.Enemies.FlyEnemies.AllFlyEnemies;
import com.mygdx.Sprite.Enemies.FlyEnemies.Bat;
import com.mygdx.Sprite.Enemies.FlyEnemies.BeeBear;
import com.mygdx.Sprite.Enemies.GroundEnemies.*;
import com.mygdx.Sprite.Enemies.WaterEnemies.AllWaterEnemy;
import com.mygdx.Sprite.Enemies.WaterEnemies.Fish;
import com.mygdx.Sprite.Enemies.WaterEnemies.Murena;
import com.mygdx.Sprite.Items.Apples;
import com.mygdx.Sprite.Items.Health;
import com.mygdx.Sprite.Items.Items;
import com.mygdx.Sprite.Objects.CheckPoint;
import com.mygdx.Sprite.Objects.DragonRing;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 07.08.17.
 */
public class WorldCreator_2 {

    private Array<AllFlyEnemies> flyEnemiesArray;
    private Array<Items> itemsArray;
    private Array<DragonRing> dragonRingsArray;
    private Array<AllWaterEnemy> waterEnemiesArray;
    private Array<CheckPoint> checkPointArray;

    public WorldCreator_2 (AllLevels lvl) {

        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        itemsArray = new Array<>();
        flyEnemiesArray = new Array<>();
        dragonRingsArray = new Array<>();
        waterEnemiesArray = new Array<>();
        checkPointArray = new Array<>();


        //Забираем из карты все что помечено как "Земля" и рисуем её физику
        for (MapObject object : lvl.getMap().getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = lvl.getWorld().createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getHeight() / 2) / MyGdxGame.PPM);
            fDef.shape = shape;
            fDef.filter.categoryBits = MyGdxGame.GROUND_BIT;
            fDef.filter.maskBits = MyGdxGame.ENEMY_BIT | MyGdxGame.TWILIGHT_BIT | MyGdxGame.TWILIGHT_HIT_BIT | MyGdxGame.ENEMY_HIT_BIT | MyGdxGame.APPLEJACK_BIT;
            body.createFixture(fDef).setUserData(this);
        }


        //Забираем из карты финальную область
        for (MapObject object : lvl.getMap().getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = lvl.getWorld().createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getHeight() / 2) / MyGdxGame.PPM);
            fDef.shape = shape;
            fDef.filter.categoryBits = MyGdxGame.FINAL_BIT;
            fDef.filter.maskBits = MyGdxGame.TWILIGHT_BIT | MyGdxGame.TWILIGHT_HIT_BIT;
            fDef.isSensor = true;
            body.createFixture(fDef).setUserData(this);
        }



        //Объекты на воде
        for (MapObject object : lvl.getMap().getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            dragonRingsArray.add(new DragonRing(lvl, rect));
        }


        //Летающие враги
        for (MapObject object : lvl.getMap().getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            int z = 1 + (int) (Math.random() * 3);
            switch (z) {
                case 1:
                    flyEnemiesArray.add(new BeeBear(lvl, rect));
                    break;
                case 2:
                    flyEnemiesArray.add(new Bat(lvl, rect));
                    break;
                default:
                    break;
            }

        }


        //Водные враги
        for (MapObject object : lvl.getMap().getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            int z = 1 + (int) (Math.random() * 3);
            switch (z) {
                case 1:
                    waterEnemiesArray.add(new Fish(lvl, rect));
                    break;
                case 2:
                    waterEnemiesArray.add(new Murena(lvl, rect));
                    break;
                default:
                    break;
            }

        }


        //Создаем яблоки

        for (MapObject object : lvl.getMap().getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            itemsArray.add(new Apples(lvl, rect));
        }

        //Создаем зелья здоровья

        for (MapObject object : lvl.getMap().getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            itemsArray.add(new Health(lvl, rect));
        }

        //Создаем чекпоинты
        int num = 0;
        for (MapObject object : lvl.getMap().getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            checkPointArray.add(new CheckPoint(lvl, rect, num));
            num++;
        }


    }

    public Array<AllFlyEnemies> getFlyEnemies () {
        return flyEnemiesArray;
    }

    public Array<Items> getItemsArray() {
        return itemsArray;
    }

    public Array<DragonRing> getDragonRingsArray() {
        return dragonRingsArray;
    }

    public Array<AllWaterEnemy> getWaterEnemiesArray() {
        return waterEnemiesArray;
    }

    public Array<CheckPoint> getCheckPointArray() {
        return checkPointArray;
    }

    public void dispose () {

        for (int i = 0; i < flyEnemiesArray.size; i++)
            flyEnemiesArray.get(i).dispose();

        for (int i = 0; i < itemsArray.size; i++)
            itemsArray.get(i).dispose();

        for (int i = 0; i < dragonRingsArray.size; i++)
            dragonRingsArray.get(i).dispose();

        for (int i = 0; i < waterEnemiesArray.size; i++)
            waterEnemiesArray.get(i).dispose();

        itemsArray.clear();
        flyEnemiesArray.clear();
        dragonRingsArray.clear();
        waterEnemiesArray.clear();
    }

}
