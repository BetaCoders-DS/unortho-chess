package com.github.antcursor.pieces.move;

import com.github.antcursor.types.Position;

/**
 * MoveResult
 */
public record MoveCandidate(Position from, Position to, MoveType type) {
}
