package com.github.antcursor.pieces.move;

import java.util.Optional;

import com.github.antcursor.pieces.PieceType;
import com.github.antcursor.types.Position;

/*
 * MoveType
*/
public sealed interface MoveType {
  public record Normal() implements MoveType {
  }

  public record Capture(PieceType captured) implements MoveType {
  }

  public record EnPassant(Position capturePos) implements MoveType {
  }

  public record CastleKing() implements MoveType {
  }

  public record CastleQueen() implements MoveType {
  }

  public record Promotion(PieceType promotedTo, Optional<PieceType> captured) {
  }
}
