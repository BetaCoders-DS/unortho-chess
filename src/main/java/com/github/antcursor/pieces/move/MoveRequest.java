package com.github.antcursor.pieces.move;

import java.util.Optional;

import com.github.antcursor.pieces.PieceType;
import com.github.antcursor.types.Position;

/**
 * MoveRequest
 */
public record MoveRequest(Position from, Position to, Optional<PieceType> promoteTo) {
}
