package com.mygdx.Screen.Dialogs;

import com.mygdx.Screen.Levels.AllLevels;

public class Dialog_1lvl_start extends AllDialog {
    public Dialog_1lvl_start(AllLevels lvl) {
        super(lvl);
    }

    @Override
    public void update() {
        super.update();
    }

    public void changeDialog (int i) {
        switch (i) {

            case 0:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Applejack-behaviour");
                bubble.setDrawable(skinIcon, "dialog_orange");
                textDialog = "Острожно, Твайлайт, впереди нас встречают жители Вечносвободного Леса. И, сдается мне, они нам не рады!";
                break;

            case 1:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_pff");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Не страшно. Я как раз на днях прочитала про одно боевое заклинание.";
                break;

            case 2:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Правда, до сих пор мне не доводилось его использовать. Как оно там звучало....";
                break;

            case 3:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "\"Нажми F для стрельбы.\"...  Понятия не имею, что это значит, но пока не попробую - не узнаю";
                break;

            case 4:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Еще в той книге было заклинание \"Нажми Space для прыжка\", но уж прыгать-то любая пони может.";
                break;

            case 5:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Applejack-behaviour");
                bubble.setDrawable(skinIcon, "dialog_orange");
                textDialog = "В любом случае, этот Лес не место для прогулок пони, так что нам лучше продвигаться медленно и осторожно";
                break;

            case 6:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Applejack-lookdown");
                bubble.setDrawable(skinIcon, "dialog_orange");
                textDialog = "Что это ?!";
                break;
            case 7:
            default:
                lvl.getApplejack().ajFallInStart();
                lvl.pause = false;
                lvl.getGame().setDialog_1lvl_start(false);
                break;

        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
