package com.github.antcursor;

import processing.core.PApplet;

/**
 * Sketch
 */
class Sketch extends PApplet {
  private Config config;

  Sketch(Config cfg) {
    config = cfg;
  }

  @Override
  public void settings() {
    size(config.width(), config.height());
  }

  @Override
  public void setup() {
    // TODO Auto-generated method stub
    super.setup();
  }

  @Override
  public void draw() {
    // TODO Auto-generated method stub
    super.draw();
  }
}
