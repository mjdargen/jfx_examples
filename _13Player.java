package jfx_examples;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

/**
 * The Player represents the controllable character.
 * 
 * Handles physics (gravity, velocity), jumping, and movement.
 */
public class _13Player extends Rectangle {

  private double xVel = 0;
  private double yVel = 0;
  private boolean onGround = false;
  private Bounds previousBounds = null;

  // Physics constants
  private static final double MOVE_SPEED = 200; // pixels per second
  private static final double JUMP_VELOCITY = -500;
  private static final double GRAVITY = 1000; // pixels per second^2

  /**
   * Constructs a rectangular player at the specified position and size.
   */
  public _13Player(double x, double y, double width, double height, Color color) {
    super(width, height, color);
    setLayoutX(x);
    setLayoutY(y);
  }

  /** Moves the player left by setting horizontal velocity. */
  public void moveLeft() {
    xVel = -MOVE_SPEED;
  }

  /** Moves the player right by setting horizontal velocity. */
  public void moveRight() {
    xVel = MOVE_SPEED;
  }

  /** Stops horizontal movement when no direction keys are pressed. */
  public void stop() {
    xVel = 0;
  }

  /** Initiates a jump if the player is currently on the ground. */
  public void jump() {
    if (onGround) {
      yVel = JUMP_VELOCITY;
      onGround = false;
    }
  }

  /**
   * Updates the player each frame:
   * - Applies gravity
   * - Updates position
   * - Clamps within screen bounds
   */
  public void update(double deltaTime) {
    // Record current bounds for collision checks
    previousBounds = getBoundsInParent();

    // Apply gravity when airborne
    if (!onGround) {
      yVel += GRAVITY * deltaTime;
    }

    // Integrate position using velocity and delta time
    setLayoutX(getLayoutX() + xVel * deltaTime);
    setLayoutY(getLayoutY() + yVel * deltaTime);

    // Prevent player from leaving the left edge
    if (getLayoutX() < 0)
      setLayoutX(0);
  }

  /**
   * Called when the player lands on a platform.
   * Aligns the player's bottom with the platform top.
   */
  public void landOnPlatform(double platformTopY) {
    setLayoutY(platformTopY - getHeight());
    yVel = 0;
    onGround = true;
    previousBounds = getBoundsInParent();
  }

  /** Sets the ground/airborne state. */
  public void setOnGround(boolean state) {
    onGround = state;
  }

  /** Returns the player's current vertical velocity. */
  public double getyVel() {
    return yVel;
  }

  /**
   * Returns the player's bounds from the previous frame for collision detection.
   */
  public Bounds getPreviousBounds() {
    if (previousBounds == null)
      return getBoundsInParent();
    return previousBounds;
  }
}
