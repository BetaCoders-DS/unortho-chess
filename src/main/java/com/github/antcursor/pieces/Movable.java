package com.github.antcursor.pieces;

import java.util.List;

import com.github.antcursor.pieces.move.MoveCandidate;
import com.github.antcursor.types.Position;

/**
 * Movable
 */
public interface Movable {

  public List<MoveCandidate> getLegalMoves(Piece[][] grid, Position pos);

  public void move(MoveCandidate move);
}
