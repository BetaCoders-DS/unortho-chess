package com.github.antcursor.board;

import com.github.antcursor.pieces.Piece;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.Position;
import com.github.antcursor.pieces.move.MoveRequest;

/**
 * Board
 */
public class Board {
  private final Piece[][] grid;
  private final int files;
  private final int ranks;

  private Position enPassantTarget;

  public int ranks() {
    return ranks;
  }

  public int files() {
    return files;
  }

  public Position enPassantTarget() {
    return enPassantTarget;
  }

  public Board() {
    files = 8;
    ranks = 8;
    grid = new Piece[ranks][files];
  }

  public Board(int files, int ranks) {
    this.files = files;
    this.ranks = ranks;
    grid = new Piece[ranks][files];
  }

  // TODO: implement Board
  public boolean isLegalMove(final MoveRequest move) {
    // TODO: implement method
    return false;
  }

  public boolean isInCheck(final Color color) {
    // TODO: implement method
    return false;
  }

  public void makeMove(final MoveRequest move) {
    // TODO: implement method
  }

  public char[][] getFENBoard() {
    // TODO: implement method
    return null;
  }

  public Piece getPiece(final Position pos) {
    return grid[pos.y()][pos.x()];
  }

  public boolean isOnBoard(final Position pos) {
    return (pos.x() < files && pos.x() >= 0)
        && (pos.y() < ranks && pos.y() >= 0);
  }
}
