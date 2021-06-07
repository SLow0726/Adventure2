package com.slow.adventure.Game.tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.slow.adventure.Game.GameDisplay;


public abstract class Tile {

    private Bitmap sprite;

    public Tile(Bitmap sprite) {
        this.sprite = sprite;
    }

    public Bitmap getSprite() {
        return sprite;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
    }
}
