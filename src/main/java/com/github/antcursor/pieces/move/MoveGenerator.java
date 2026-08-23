package com.github.antcursor.pieces.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.antcursor.board.Board;
import com.github.antcursor.pieces.Piece;
import com.github.antcursor.pieces.PieceType;
import com.github.antcursor.pieces.move.PieceMovement.PawnMovement;
import com.github.antcursor.pieces.move.PieceMovement.SimpleMovement;
import com.github.antcursor.types.Position;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.Direction;

/**
 * MoveGenerator
 * Generates move candidates, does not check legality.
 */
public class MoveGenerator {
  public static List<MoveCandidate> from(Position position, Board board) {
    Piece piece = board.getPiece(position);

    switch (piece.type().movement()) {
      case SimpleMovement sp -> {
        return simpleMoves(sp, piece.color(), position, board);
      }
      case PawnMovement pm -> {
        return pawnMoves(piece.color(), position, board);
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

  private static List<MoveCandidate> pawnMoves(Color color, Position pos, Board board) {
    List<MoveCandidate> moves = new ArrayList<>();

    boolean moved = 1 < pos.colorRelativeRank(
        color,
        board.ranks());

    Position front = pos.front(color);
    MoveType type = new MoveType.Normal();

    if (board.isOnBoard(front) && board.getPiece(front) == null) {
      addPawnCandidate(moves, pos, front, type, color, board);

      if (!moved) {
        Position front2 = front.front(color);
        if (board.isOnBoard(front2) && board.getPiece(front2) == null)
          addPawnCandidate(moves, pos, front2, type, color, board);
      }
    }

    Position front_east = front.addDirection(Direction.E);
    Position front_west = front.addDirection(Direction.W);

    Piece target;
    if (board.isOnBoard(front_east) &&
        (target = board.getPiece(front_east)) != null
        && target.color() != color) {

      type = new MoveType.Capture(target.type());
      addPawnCandidate(moves, pos, front_east, type, color, board);
    }
    if (board.isOnBoard(front_west) &&
        (target = board.getPiece(front_west)) != null
        && target.color() != color) {

      type = new MoveType.Capture(target.type());
      addPawnCandidate(moves, pos, front_west, type, color, board);
    }

    if (front_east.equals(board.enPassantTarget())) {
      type = new MoveType.EnPassant(
          front_east.behind(color));
      addPawnCandidate(moves, pos, front_east, type, color, board);
    }
    if (front_west.equals(board.enPassantTarget())) {
      type = new MoveType.EnPassant(
          front_west.behind(color));
      addPawnCandidate(moves, pos, front_west, type, color, board);
    }

    return moves;
  }

  private static void addPawnCandidate(
      List<MoveCandidate> moves,
      Position from, Position to,
      MoveType type, Color color, Board board) {

    boolean promotes = to.colorRelativeRank(
        color,
        board.ranks()) == (board.ranks() - 1);

    if (!promotes) {
      moves.add(new MoveCandidate(from, to, type));
      return;
    }
    Optional<PieceType> captured = switch (type) {
      case MoveType.Capture c -> Optional.of(c.captured());
      case MoveType.EnPassant ep -> Optional.of(PieceType.PAWN);
      default -> Optional.empty();
    };
    for (PieceType promo : List.of(
        PieceType.QUEEN, PieceType.ROOK,
        PieceType.BISHOP, PieceType.KNIGHT)) {
      moves.add(new MoveCandidate(from, to, new MoveType.Promotion(promo, captured)));
    }
  }
}
