package com.github.antcursor.pieces.move;

import java.util.ArrayList;
import java.util.List;

import com.github.antcursor.board.Board;
import com.github.antcursor.pieces.Piece;
import com.github.antcursor.pieces.move.PieceMovement.PawnMovement;
import com.github.antcursor.pieces.move.PieceMovement.SimpleMovement;
import com.github.antcursor.types.Position;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.Direction;

/**
 * MoveGenerator
 */
public class MoveGenerator {
  public static List<MoveCandidate> from(Position position, Board board) {
    Piece piece = board.getPiece(position);

    switch (piece.type().movement()) {
      case SimpleMovement sp -> {
        return simpleMoves(sp, piece.color(), position, board);
      }
      case PawnMovement pm -> {
      }
    }
  }

  private static List<MoveCandidate> simpleMoves(SimpleMovement sp, Color color, Position pos, Board board) {
    List<MoveCandidate> moves = new ArrayList<>();

    directions: for (Direction dir : sp.dirs()) {
      Position to = pos.addDirection(dir);
      MoveType type = new MoveType.Normal();

      boolean end = !sp.slide();
      do {
        if (!board.isOnBoard(to))
          continue directions;

        Piece target = board.getPiece(to);
        if (target != null) {
          if (target.color() == color)
            continue directions;

          type = new MoveType.Capture(target.type());
          end = true;
        }

        moves.add(
            new MoveCandidate(
                pos,
                to,
                type));

        to = to.addDirection(dir);
      } while (!end);
    }

    return moves;
  }

      }
    }

    return moves;
  }
}
