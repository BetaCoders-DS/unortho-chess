package com.github.antcursor.pieces.move;

import java.util.ArrayList;
import java.util.List;

import com.github.antcursor.board.Board;
import com.github.antcursor.pieces.Piece;
import com.github.antcursor.pieces.move.PieceMovement.PawnMovement;
import com.github.antcursor.pieces.move.PieceMovement.SimpleMovement;
import com.github.antcursor.pieces.move.PieceMovement.SimpleMovement.Direction;
import com.github.antcursor.types.Position;

/**
 * MoveGenerator
 */
public class MoveGenerator {
  public static List<MoveCandidate> from(Position position, Board board) {
    Piece piece = board.getPiece(position);

    List<MoveCandidate> moves = new ArrayList<>();

    switch (piece.type().movement()) {
      case SimpleMovement sp -> {

        directions: for (Direction dir : sp.dirs()) {
          Position to = position.addDirection(dir);
          MoveType type = new MoveType.Normal();

          boolean end = !sp.slide();
          do {
            if (!board.isOnBoard(to))
              continue directions;

            Piece target = board.getPiece(to);
            if (target != null) {
              if (target.color() == piece.color())
                continue directions;

              type = new MoveType.Capture(target.type());
              end = true;
            }

            moves.add(
                new MoveCandidate(
                    position,
                    to,
                    type));

            to = to.addDirection(dir);
          } while (!end);
        }
      }
      case PawnMovement pm -> {
      }
    }

    return moves;
  }
}
