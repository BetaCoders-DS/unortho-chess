package com.github.antcursor.pieces;

import java.util.List;

import com.github.antcursor.pieces.move.MoveCandidate;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.Position;

/**
 * Piece
 */
public abstract class Piece implements Movable {
  private Color color;

  public class King extends Piece {
    @Override
    public List<MoveCandidate> getLegalMoves(Piece[][] grid, Position pos) {
      // TODO: implement method
      return null;
    }

    @Override
    public void move(MoveCandidate move) {
      // TODO: implement method

    }
  }

  public class Queen extends Piece {
    @Override
    public List<MoveCandidate> getLegalMoves(Piece[][] grid, Position pos) {
      // TODO: implement method
      return null;
    }

    @Override
    public void move(MoveCandidate move) {
      // TODO: implement method

    }
  }

  public class Rook extends Piece {
    @Override
    public List<MoveCandidate> getLegalMoves(Piece[][] grid, Position pos) {
      // TODO: implement method
      return null;
    }

    @Override
    public void move(MoveCandidate move) {
      // TODO: implement method

    }
  }

  public class Bishop extends Piece {
    @Override
    public List<MoveCandidate> getLegalMoves(Piece[][] grid, Position pos) {
      // TODO: implement method
      return null;
    }

    @Override
    public void move(MoveCandidate move) {
      // TODO: implement method

    }
  }

  public class Knight extends Piece {
    @Override
    public List<MoveCandidate> getLegalMoves(Piece[][] grid, Position pos) {
      // TODO: implement method
      return null;
    }

    @Override
    public void move(MoveCandidate move) {
      // TODO: implement method

    }
  }

  public class Pawn extends Piece {
    @Override
    public List<MoveCandidate> getLegalMoves(Piece[][] grid, Position pos) {
      // TODO: implement method
      return null;
    }

    @Override
    public void move(MoveCandidate move) {
      // TODO: implement method

    }
  }

  public Color color() {
    return color;
  }
}
