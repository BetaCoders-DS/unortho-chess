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
}
