package com.github.antcursor.types;

/**
 * Position
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
}
