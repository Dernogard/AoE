package com.mygdx.Screen.Dialogs;

import com.mygdx.Screen.Levels.AllLevels;

public class Dialog_2lvl_start extends AllDialog {

    public Dialog_2lvl_start(AllLevels lvl) {
        super(lvl);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void changeDialog(int i) {
        switch (i) {

            case 0:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Rarity_Emm");
                bubble.setDrawable(skinIcon, "dialog_rarity");
                textDialog = "Ох, вы только взгляните на это озеро!";
                break;

            case 1:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Rarity_Emm");
                bubble.setDrawable(skinIcon, "dialog_rarity");
                textDialog = "С ним определенно что-то не так.";
                break;

            case 2:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Rarity_Eww");
                bubble.setDrawable(skinIcon, "dialog_rarity");
                textDialog = "У меня такое чувство, что впереди нас ожидает суровое испытание.";
                break;

            case 3:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Applejack-scared");
                bubble.setDrawable(skinIcon, "dialog_orange");
                textDialog = "Тут я с тобой согласна, подруга. Пересечь озеро будет очень не просто.";
                break;

            case 4:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think2");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Знаете, у меня есть идея.";
                break;

            case 5:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think2");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "В одной из книг я читала про вот эти штуковины. ";
                break;

            case 6:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think2");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Там было сказано, что это, своего рода, стационарное телепортационное заклинание.";
                break;
            case 7:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Думаю, у меня хватит магических сил активировать их, нужно только подойти к ним ближе.";
                break;
            case 8:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi_think");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Достаточно будет лишь мне одной перебраться на другую сторону. И тогда, с помощью этой штуковины, я перенесу и вас.";
                break;
            case 9:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "Applejack-smile");
                bubble.setDrawable(skinIcon, "dialog_orange");
                textDialog = "Это определенно хорошие новости, Твайлайт. Что ж, тогда не будем медлить!";
                break;
            case 10:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi-serious");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Верно, судьба всей Эквестрии зависит от нас.";
                break;
            case 11:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "pinkie_listen_something");
                bubble.setDrawable(skinIcon, "dialog_pink");
                textDialog = "А? \nАга. \nЯсненько. \nХорошо, я передам ей.";
                break;
            case 12:
                iconLeft.setVisible(true);
                iconRight.setVisible(false);
                iconLeft.setDrawable(skinIcon, "pinkie_listen_something");
                bubble.setDrawable(skinIcon, "dialog_pink");
                textDialog = "Твайлайт, меня тут попросили передать тебе, что на озере ты можешь колдовать кнопку E";
                break;
            case 13:
                iconLeft.setVisible(false);
                iconRight.setVisible(true);
                iconRight.setDrawable(skinIcon, "Twi-v_ahue");
                bubble.setDrawable(skinIcon, "dialog_violent");
                textDialog = "Эээ... ммм... ладно...";
                break;
            default:
                lvl.pause = false;
                lvl.getGame().setDialog_2lvl_start(false);
                break;

        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
