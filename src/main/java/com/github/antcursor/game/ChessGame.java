package com.github.antcursor.game;

import java.util.ArrayList;
import java.util.List;

import com.github.antcursor.board.Board;
import com.github.antcursor.types.Color;
import com.github.antcursor.pieces.move.MoveCandidate;
import com.github.antcursor.pieces.move.MoveRequest;
import com.github.antcursor.pieces.move.MoveResult;
import com.github.antcursor.types.Position;
import com.github.antcursor.pieces.Piece;

public class ChessGame {
  private Board board;
  private Color turn;
  private List<MoveResult> moveHistory;

  public char[][] getFENBoard() {
    return board.getFENBoard();
  }

  public ChessGame() {
    this.board = new Board();
    this.turn = Color.WHITE;
    this.moveHistory = new ArrayList<>();
  }

  public List<MoveCandidate> getPossibleMoves(Position pos) {
    Piece piece = board.getPiece(pos);

    if (piece == null || piece.color() != turn) {
      return List.of();
    }

    return board.getLegalMoves(pos);
  }

  public boolean tryMove(MoveRequest move) {
    if (!board.isLegalMove(move)) {
      return false;
    }

    MoveResult result = board.makeMove(move);
    moveHistory.add(result);

    turn = (turn == Color.WHITE) ? Color.BLACK : Color.WHITE;
    return true;
  }
}
