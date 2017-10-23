package com.mygdx.Sprite.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.Screen.Levels.AllLevels;
import com.mygdx.Sprite.Hero.Twilight;
import com.mygdx.game.MyGdxGame;

/**
 * Created by velz on 27.07.17.
 */
public class Health extends  Items {

    public Health(AllLevels lvl, Rectangle bounds) {
        super(lvl, bounds);

        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.APPLE_BIT);

        texture = new TextureRegion(new Texture("resourse/potionRed.png"), 0,0,64,64);
        setBounds(0,0,64 / MyGdxGame.PPM,64 / MyGdxGame.PPM);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y  - getHeight() / 2);
        setRegion(texture);

        usedSound = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound/smb_powerup.wav"));


    }

    @Override
    public void takeItem(Twilight player) {
        if (MyGdxGame.isVolumeOn())
            usedSound.play(MyGdxGame.getVOLUME());

        setCategoryFilter(MyGdxGame.NOTHING_BIT);
        setAlpha(0);
        player.addLives();
        player.setScore(100);


    }
}
