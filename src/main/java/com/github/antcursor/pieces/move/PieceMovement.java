package com.github.antcursor.pieces.move;

import java.util.List;
import com.github.antcursor.types.Direction;

/**
 * PieceMovement
 */
public sealed interface PieceMovement {

  /**
   * PawnMovement
   */
  public record PawnMovement() implements PieceMovement {
  }

  /**
   * SimpleMovement
   */
  public record SimpleMovement(List<Direction> dirs, boolean slide) implements PieceMovement {
  }
}
