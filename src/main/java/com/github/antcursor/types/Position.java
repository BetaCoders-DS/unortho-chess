package com.github.antcursor.types;

/**
 * Position
 * 0,0 being the up-left corner
 */
public record Position(int rank, int file) {
  @Override
  public boolean equals(Object other) {
    if (other == this)
      return true;

    if (!(other instanceof Position pos))
      return false;

    return this.rank() == pos.rank() && this.file() == pos.file();
  }

  public static Position behind(Position pos, Color pieceColor) {
    switch (pieceColor) {
      case WHITE:
        return new Position(pos.rank + 1, pos.file);
      case BLACK:
        return new Position(pos.rank - 1, pos.file);
      default:
        return null;
    }
  }

  public Position addDirection(Direction dir) {
    return new Position(rank() + dir.dy(), file() + dir.dx());
  }

  public int x() {
    return file;
  }

  public int y() {
    return rank;
  }
}
