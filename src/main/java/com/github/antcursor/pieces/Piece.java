package com.github.antcursor.pieces;

import com.github.antcursor.types.Color;
import com.github.antcursor.types.Position;

public abstract class Piece implements Movable{
    private Color color;
    protected int moveCount;

    public Piece(Color color) {
        color = this.color;
    }

    public Color color() {
        return this.color;
    }
}
