package com.mygdx.game;

import static com.mygdx.game.BugAttack.SCR_HEIGHT;
import static com.mygdx.game.BugAttack.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class BugEntity {
    float x, y;
    float vx, vy;
    float width, height;
    float angle;

    public BugEntity() {
        respawn();
    }

    void move() {
        x += vx;
        y += vy;
        if(x>SCR_WIDTH){
            respawn();
        }
        angle = 270;
    }

    void respawn() {
        width = MathUtils.random(40f, 60f);
        height = width;
        x = -100;
        y = MathUtils.random(height*2, SCR_HEIGHT);
        vy = 0;
        vx = MathUtils.random(0.5f, 2f);
    }

    boolean hit(float tx, float ty) {
        return x<tx & tx<x+width & y<ty & ty<y+height;
    }
}