package com.github.antcursor.pieces.move;

import com.github.antcursor.pieces.Piece;
import com.github.antcursor.types.Position;

/**
 * MoveResult
 */
public record MoveResult(
    MoveRequest request,
    MoveType type,
    Piece movedPiece) {

  public Position capturedPosition() {
    return type instanceof MoveType.EnPassant ep ? ep.capturePos() : request.to();
  }
}
