package com.mygdx.game;

import static com.mygdx.game.BugAttack.SCR_HEIGHT;
import static com.mygdx.game.BugAttack.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class BugRed extends BugEntity{

    @Override
    void move() {
        x += vx;
        y += vy;
        if(x>SCR_WIDTH){
            respawn();
        }
        angle = 270;
    }
    void respawn() {
        width = MathUtils.random(60f, 80f);
        height = width;
        x = -100;
        y = MathUtils.random(height*2, SCR_HEIGHT);
        vy = 0;
        vx = MathUtils.random(2f, 3f);
    }
}
