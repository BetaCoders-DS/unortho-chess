package com.github.antcursor.types;

/**
 * Position
 */
public record Position(int rank, int file) {
  @Override
  public boolean equals(Object other) {
    // TODO: implment equality logic for position
    return false;
  }
}
