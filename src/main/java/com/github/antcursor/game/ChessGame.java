package com.github.antcursor.game;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

  public char[][] getFENBoard() {
    return board.getFENBoard();
  }

  public GameState getState() {
    return state;
  }

  public Color turn() {
    return turn;
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

  private static final Path DEFAULT_BOARD_PATH = Paths.get("src/main/resources/boards", "classic.csv");

  public ChessGame() {
    this.board = new Board();
    try {
      this.board.fromCSV(DEFAULT_BOARD_PATH);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load default board: " + DEFAULT_BOARD_PATH, e);
    }
    this.turn = Color.WHITE;
    this.state = GameState.WHITE_TURN;
    this.moveHistory = new ArrayList<>();
  }

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

  public boolean undoMove() {
    if (moveHistory.isEmpty())
      return false;

    MoveResult last = moveHistory.remove(moveHistory.size() - 1);
    board.undoMove(last);
    turn = opposite(turn);
    updateGameState();
    return true;
  }
}
