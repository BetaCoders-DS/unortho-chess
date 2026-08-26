package com.github.antcursor.render;

import com.github.antcursor.game.ChessGame;
import com.github.antcursor.types.Position;

/**
 * RenderI
 */
public interface RenderI {
  public void render(ChessGame game);

  /**
   * Maps a screen point to a board Position. Null if outside the board. Honors
   * current flip.
   */
  public Position screenToBoard(float screenX, float screenY, ChessGame game);
}
