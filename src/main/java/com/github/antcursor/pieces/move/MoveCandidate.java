package com.github.antcursor.pieces.move;

import java.util.Optional;

import com.github.antcursor.pieces.PieceType;
import com.github.antcursor.types.Position;

/**
 * MoveResult
 */
public record MoveCandidate(Position from, Position to, MoveType type, Optional<PieceType> promoteTo) {
}
