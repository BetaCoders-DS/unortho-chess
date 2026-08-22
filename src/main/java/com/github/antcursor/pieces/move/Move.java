package com.github.antcursor.pieces.move;

import com.github.antcursor.pieces.Piece;
import com.github.antcursor.types.Position;

/**
 * Move
 */
public record Move(Position from, Position to, MoveType type, Piece promoteTo) {
}
