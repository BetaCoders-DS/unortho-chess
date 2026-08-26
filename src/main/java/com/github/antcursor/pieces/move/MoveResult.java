package com.github.antcursor.pieces.move;

import com.github.antcursor.pieces.Piece;
import com.github.antcursor.types.Position;

/*
 * MoveResult
*/
public record MoveResult(
    Position from,
    Position to,
    MoveType type,
    Piece movedPiece,
    Piece capturedPiece,
    Position prevEnPassantTarget,
    boolean prevWhiteKingSide,
    boolean prevWhiteQueenSide,
    boolean prevBlackKingSide,
    boolean prevBlackQueenSide) {

  public Position capturedPosition() {
    return type instanceof MoveType.EnPassant ep ? ep.capturePos() : to();
  }
}
