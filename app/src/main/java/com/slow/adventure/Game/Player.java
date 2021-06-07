package com.slow.adventure.Game;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.slow.adventure.Game.JoyStick.Joystick;
import com.slow.adventure.R;

public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 5;
    private Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints = MAX_HEALTH_POINTS;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context, this);
    }

    public void update() {

        // Update velocity based on actuator of joystick
//        velocityX = joystick.getActuatorX()*MAX_SPEED;
//        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        super.draw(canvas, gameDisplay);
        healthBar.draw(canvas, gameDisplay);
    }

    public int getHealthPoint() {
        return healthPoints;
    }

    public void setHealthPoint(int healthPoints) {
        // Only allow positive values
        if (healthPoints >= 0)
            this.healthPoints = healthPoints;
    }
}
