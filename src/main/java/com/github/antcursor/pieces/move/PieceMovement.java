package com.github.antcursor.pieces.move;

import java.util.List;

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
    /**
     * Direction
     */
    public enum Direction {
      N(0, 1),
      S(0, -1),
      E(1, 0),
      W(-1, 0),

      NE(1, 1),
      NW(-1, 1),
      SE(1, -1),
      SW(-1, -1),

      L_NNE(1, 2),
      L_NNW(-1, 2),
      L_SSE(1, -2),
      L_SSW(-1, -2),
      L_ENE(2, 1),
      L_WNW(-2, 1),
      L_ESE(2, -1),
      L_WSW(-2, -1),
      ;

      private final int dx;
      private final int dy;

      public int dx() {
        return dx;
      }

      public int dy() {
        return dy;
      }

      Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
      }
    }
  }
}
