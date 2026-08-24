package com.github.antcursor.game;

import java.util.ArrayList;
import java.util.List;

import com.github.antcursor.board.Board;
import com.github.antcursor.types.Color;
import com.github.antcursor.pieces.move.MoveCandidate;
import com.github.antcursor.pieces.move.MoveRequest;
import com.github.antcursor.pieces.move.MoveResult;
import com.github.antcursor.types.Position;

public class ChessGame {
  private Board board;
  private Color turn;
  private List<MoveResult> moveHistory;

  public ChessGame() {
    this.board = new Board();
    this.turn = Color.WHITE;
    this.moveHistory = new ArrayList<>();
  }

  public List<MoveCandidate> getPossibleMoves(Position pos) {
    // TODO: implement method
    return null;
  }

  public boolean tryMove(MoveRequest move) {
    // TODO: implement method
    return false;
  }

  public Board board() {
    return board;
  };
}
