package com.github.antcursor.board;

import com.github.antcursor.pieces.Piece;
import com.github.antcursor.types.Color;
import com.github.antcursor.pieces.move.MoveCandidate;

/**
 * Board
 */
public record Board(Piece[][] grid, int files, int ranks) {
  // TODO: implement Board
  public boolean isLegalMove(MoveCandidate move) {
    // TODO: implement method
    return false;
  }

  public boolean isInCheck(Color color) {
    // TODO: implement method
    return false;
  }

  public void makeMove(MoveCandidate move) {
    // TODO: implement method
  }

  public char[][] getFENBoard() {
    // TODO: implement method
    return null;
  }
}
