package com.github.antcursor.pieces;

import java.util.List;

import com.github.antcursor.pieces.move.PieceMovement;
import com.github.antcursor.types.Direction;

/**
 * PieceType
 */
public enum PieceType {
  NONE(),
  PAWN(new PieceMovement.PawnMovement()),
  KNIGHT(new PieceMovement.SimpleMovement(
      List.of(
          Direction.L_NNE,
          Direction.L_NNW,
          Direction.L_ENE,
          Direction.L_ESE,
          Direction.L_SSE,
          Direction.L_SSW,
          Direction.L_WSW,
          Direction.L_WNW),
      false)),
  BISHOP(new PieceMovement.SimpleMovement(
      List.of(
          Direction.NE,
          Direction.NW,
          Direction.SE,
          Direction.SW),
      true)),
  ROOK(new PieceMovement.SimpleMovement(
      List.of(
          Direction.N,
          Direction.E,
          Direction.S,
          Direction.W),
      true)),
  QUEEN(new PieceMovement.SimpleMovement(
      List.of(
          Direction.N,
          Direction.E,
          Direction.S,
          Direction.W,
          Direction.NE,
          Direction.NW,
          Direction.SE,
          Direction.SW),
      true)),
  KING(new PieceMovement.SimpleMovement(
      List.of(
          Direction.N,
          Direction.E,
          Direction.S,
          Direction.W,
          Direction.NE,
          Direction.NW,
          Direction.SE,
          Direction.SW),
      false)),;

  private final PieceMovement movement;

  public PieceMovement movement() {
    return movement;
  }

  PieceType() {
    this.movement = null;
  }

  PieceType(PieceMovement mov) {
    this.movement = mov;
  }
}
