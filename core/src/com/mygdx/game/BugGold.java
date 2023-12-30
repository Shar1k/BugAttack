package com.mygdx.game;

import static com.mygdx.game.BugAttack.SCR_HEIGHT;
import static com.mygdx.game.BugAttack.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class BugGold extends BugEntity{

    @Override
    void move() {
        x += vx;
        y += MathUtils.random(-10F, 10F);
        if(x>SCR_WIDTH){
            respawn();
        }
        angle = 270;
    }
    void respawn() {
        width = MathUtils.random(10f, 20f);
        height = width;
        x = -100;
        y = MathUtils.random(height*2, SCR_HEIGHT);
        vx = MathUtils.random(7f, 9f);
    }
}
