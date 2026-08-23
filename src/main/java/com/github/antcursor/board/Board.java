package com.github.antcursor.board;

import com.github.antcursor.pieces.Piece;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.Position;
import com.github.antcursor.pieces.move.MoveRequest;

/**
 * Board
 */
public record Board(Piece[][] grid, int files, int ranks) {
  // TODO: implement Board
  public boolean isLegalMove(MoveRequest move) {
    // TODO: implement method
    return false;
  }

  public boolean isInCheck(Color color) {
    // TODO: implement method
    return false;
  }

  public void makeMove(MoveRequest move) {
    // TODO: implement method
  }

  public char[][] getFENBoard() {
    // TODO: implement method
    return null;
  }

  public Piece getPiece(Position pos) {
    return grid[pos.y()][pos.x()];
  }

  public boolean isOnBoard(Position pos) {
    return (pos.x() < files && pos.x() >= 0)
        && (pos.y() < ranks && pos.y() >= 0);
  }
}
