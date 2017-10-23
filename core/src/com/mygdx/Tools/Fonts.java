package com.mygdx.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by velz on 29.07.17.
 */
public class Fonts extends BitmapFont {

    public BitmapFont createFont (int size, Color color, String font) {

        String FONT_PATH;

        switch(font) {
            case "fontinBI":
                FONT_PATH = "Fonts/FontinSans_Cyrillic_BI_46b.otf";
                break;
            case "fontinI":
                FONT_PATH = "Fonts/FontinSans_Cyrillic_I_46b.otf";
                break;
            case "anime":
            default:
                FONT_PATH = "Fonts/AnimeAce2.ttf";
                break;
        }

        String FONT_CHAR = "";

        for (int i = 32; i < 127; i++) FONT_CHAR += (char)i;
        for (int i = 1024; i < 1104; i++) FONT_CHAR += (char)i;

        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;

        generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;
        parameter.characters = FONT_CHAR;
        parameter.color = color;

        BitmapFont font1 = generator.generateFont(parameter);
        generator.dispose();

        return font1;
    }

}
