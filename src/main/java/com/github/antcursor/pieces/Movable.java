package com.github.antcursor.pieces;

import java.util.List;

import com.github.antcursor.pieces.move.Move;
import com.github.antcursor.types.Position;

/**
 * Movable
 */
public interface Movable {

  public List<Move> getLegalMoves(Piece[][] grid, Position pos);

  public void move(Move move);
}
