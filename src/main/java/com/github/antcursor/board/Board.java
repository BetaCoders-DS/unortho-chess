package com.github.antcursor.board;

import java.util.List;

import com.github.antcursor.pieces.Piece;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.Position;
import com.github.antcursor.pieces.move.MoveRequest;
import com.github.antcursor.pieces.move.MoveCandidate;
import com.github.antcursor.pieces.move.MoveType;
import com.github.antcursor.pieces.move.MoveGenerator;
import com.github.antcursor.pieces.PieceType;
import com.github.antcursor.pieces.IllegalMoveException;

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

  public Piece getPiece(final Position pos) {
    return grid[pos.y()][pos.x()];
  }

  public void setPiece(final Position pos, final Piece piece) {
    grid[pos.y()][pos.x()] = piece;
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

  private Board(Piece[][] grid, int files, int ranks) {
    this.grid = grid;
    this.files = files;
    this.ranks = ranks;
  }

  private Piece[][] cloneGrid() {
    Piece[][] copy = new Piece[ranks][files];
    for (int y = 0; y < ranks; y++) {
      copy[y] = grid[y].clone();
    }
    return copy;
  }

  private MoveCandidate findCandidate(MoveRequest move) {
    List<MoveCandidate> candidates = MoveGenerator.from(move.from(), this);
    if (candidates == null)
      return null;

    for (MoveCandidate candidate : candidates) {
      if (candidate.to().equals(move.to())) {
        return candidate;
      }
    }
    return null;
  }

  public boolean isLegalMove(final MoveRequest move) {
    Piece piece = getPiece(move.from());
    if (piece == null)
      return false;

    MoveCandidate candidate = findCandidate(move);
    if (candidate == null)
      return false;

    Board simulated = new Board(cloneGrid(), files, ranks);
    simulated.applyMove(candidate, piece, move);

    return !simulated.isInCheck(piece.color());
  }

  private Position findKing(Color color) {
    for (int y = 0; y < ranks; ++y) {
      for (int x = 0; x < files; ++x) {
        Piece piece = grid[y][x];
        if (piece != null && piece.type() == PieceType.KING && piece.color() == color) {
          return new Position(x, y);
        }
      }
    }
    return null;
  }

  private boolean isSquareAttacked(Position target, Color byColor) {
    for (int y = 0; y < ranks; ++y) {
      for (int x = 0; x < files; ++x) {
        Piece piece = grid[y][x];
        if (piece == null || piece.color() != byColor)
          continue;

        List<MoveCandidate> candidates = MoveGenerator.from(new Position(x, y), this);
        if (candidates == null)
          continue;

        for (MoveCandidate candidate : candidates) {
          if (candidate.to().equals(target))
            return true;
        }
      }
    }
    return false;
  }

  private Color opposite(Color color) {
    return color == Color.WHITE ? Color.BLACK : Color.WHITE;
  }

  public boolean isInCheck(final Color color) {
    Position kingPos = findKing(color);

    return isSquareAttacked(kingPos, opposite(color));
  }

  public void makeMove(final MoveRequest move) {
    if (!isLegalMove(move)) {
      throw new IllegalMoveException("" + move);
    }

    Piece piece = getPiece(move.from());
    MoveCandidate candidate = findCandidate(move);

    applyMove(candidate, piece, move);
    updateEnPassantTarget(piece, move);
  }

  public char[][] getFENBoard() {
    char[][] fen = new char[ranks][files];

    for (int y = 0; y < ranks; y++) {
      for (int x = 0; x < files; x++) {
        Piece piece = grid[y][x];
        fen[y][x] = (piece == null) ? '.' : toFenChar(piece);
      }
    }

    return fen;
  }

  private char toFenChar(Piece piece) {
    // TODO: implement
    return ' ';
  }

  private void applyMove(MoveCandidate candidate, Piece piece, MoveRequest move) {
    switch (candidate.type()) {
      case MoveType.Normal normal -> {
        setPiece(move.to(), piece);
        setPiece(move.from(), null);
      }

      case MoveType.Capture capture -> {
        setPiece(move.to(), piece);
        setPiece(move.from(), null);
      }

      case MoveType.EnPassant enPassant -> {
        setPiece(move.to(), piece);
        setPiece(move.from(), null);
        setPiece(enPassant.capturePos(), null);
      }

      case MoveType.CastleKing castleKing -> {
        setPiece(move.to(), piece);
        setPiece(move.from(), null);
        int rank = move.from().y();
        Position rookFrom = new Position(files - 1, rank);
        Position rookTo = new Position(move.to().x() - 1, rank);
        Piece rook = getPiece(rookFrom);
        setPiece(rookFrom, null);
        setPiece(rookTo, rook);
      }

      case MoveType.CastleQueen castleQueen -> {
        setPiece(move.to(), piece);
        setPiece(move.from(), null);
        int rank = move.from().y();
        Position rookFrom = new Position(0, rank);
        Position rookTo = new Position(move.to().x() + 1, rank);
        Piece rook = getPiece(rookFrom);
        setPiece(rookFrom, null);
        setPiece(rookTo, rook);
      }

      case MoveType.Promotion promotion -> {
        setPiece(move.to(), new Piece(piece.color(), promotion.promotedTo()));
        setPiece(move.from(), null);
      }
    }
  }

  private void updateEnPassantTarget(Piece piece, MoveRequest move) {
    boolean twoSquarePawnMove = piece.type() == PieceType.PAWN
        && Math.abs(move.to().y() - move.from().y()) == 2;

    enPassantTarget = twoSquarePawnMove ? move.to().behind(piece.color()) : null;
  }

  public boolean isOnBoard(final Position pos) {
    return (pos.x() < files && pos.x() >= 0)
        && (pos.y() < ranks && pos.y() >= 0);
  }
}
