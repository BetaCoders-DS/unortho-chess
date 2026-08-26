package com.github.antcursor.game;

import java.util.ArrayList;
import java.util.List;

import com.github.antcursor.board.Board;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.GameState;
import com.github.antcursor.pieces.move.MoveCandidate;
import com.github.antcursor.pieces.move.MoveRequest;
import com.github.antcursor.pieces.move.MoveResult;
import com.github.antcursor.types.Position;
import com.github.antcursor.pieces.Piece;

public class ChessGame {
  private Board board;
  private Color turn;
  private GameState state;
  private List<MoveResult> moveHistory;

  public ChessGame() {
    this.board = new Board();
    this.turn = Color.WHITE;
    this.state = GameState.WHITE_TURN;
    this.moveHistory = new ArrayList<>();
  }

  public char[][] getFENBoard() {
    return board.getFENBoard();
  }

  public GameState getState() {
    return state;
  }

  public List<MoveCandidate> getPossibleMoves(Position pos) {
    if (isGameOver())
      return List.of();

    Piece piece = board.getPiece(pos);
    if (piece == null || piece.color() != turn) {
      return List.of();
    }

    return board.getLegalMoves(pos);
  }

  public boolean tryMove(MoveRequest move) {
    if (isGameOver() || !board.isLegalMove(move)) {
      return false;
    }

    MoveResult result = board.makeMove(move);
    moveHistory.add(result);

    turn = opposite(turn);
    updateGameState();
    return true;
  }

  public Board board() {
    return board;
  };

  public static final char[][] defaultBoard = {
      { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
      { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
      { '.', '.', '.', '.', '.', '.', '.', '.' },
      { '.', '.', '.', '.', '.', '.', '.', '.' },
      { '.', '.', '.', '.', '.', '.', '.', '.' },
      { '.', '.', '.', '.', '.', '.', '.', '.' },
      { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
      { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },
  };

  private boolean isGameOver() {
    return state == GameState.CHECKMATE || state == GameState.DRAW;
  }

  private Color opposite(Color color) {
    return color == Color.WHITE ? Color.BLACK : Color.WHITE;
  }

  private void updateGameState() {
    if (board.isCheckmate(turn)) {
      state = GameState.CHECKMATE;
    } else if (board.isStalemate(turn)) {
      state = GameState.DRAW;
    } else {
      state = (turn == Color.WHITE) ? GameState.WHITE_TURN : GameState.BLACK_TURN;
    }
  }
}
