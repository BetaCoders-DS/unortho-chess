package com.github.antcursor.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * BoardCSVLoader
 * Parses a comma-separated board layout file (one rank per line, FEN-style
 * piece letters, '.' for empty) into the char[][] format Board.fromFEN
 * expects.
 */
public final class BoardCSVLoader {
  private BoardCSVLoader() {
  }

  public static char[][] load(Path path) throws IOException {
    List<String> lines = Files.readAllLines(path).stream()
        .filter(line -> !line.isBlank())
        .toList();

    char[][] board = new char[lines.size()][];

    for (int r = 0; r < lines.size(); ++r) {
      String[] cells = lines.get(r).split(",");
      char[] row = new char[cells.length];
      for (int f = 0; f < cells.length; ++f) {
        row[f] = cells[f].trim().charAt(0);
      }
      board[r] = row;
    }

    return board;
  }
}
