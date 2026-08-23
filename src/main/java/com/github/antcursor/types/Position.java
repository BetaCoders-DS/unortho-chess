package com.github.antcursor.types;

/**
 * Position
 * 0,0 being the up-left corner
 */
public record Position(int x, int y) {
  @Override
  public boolean equals(Object other) {
    if (other == this)
      return true;

    if (!(other instanceof Position pos))
      return false;

    return this.y() == pos.y() && this.x() == pos.x();
  }

  public Position behind(Color pieceColor) {
    switch (pieceColor) {
      case WHITE:
        return new Position(x, y + 1);
      case BLACK:
        return new Position(x, y - 1);
      default:
        return null;
    }
  }

  public Position addDirection(Direction dir) {
    return new Position(x() + dir.dx(), y() + dir.dy());
  }
}
