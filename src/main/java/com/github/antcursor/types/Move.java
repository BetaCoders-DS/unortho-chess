package com.github.antcursor.types;

import com.github.antcursor.pieces.Piece;

/**
 * Move
 */
public record Move(Position from, Position to, MoveType type, Piece promoteTo) {
}
