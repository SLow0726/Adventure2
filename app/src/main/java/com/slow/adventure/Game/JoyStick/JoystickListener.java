package com.slow.adventure.Game.JoyStick;

public interface JoystickListener {
    void onDown();

    /**
     * @param degrees -180 -> 180.
     * @param offset  normalized, 0 -> 1.
     */
    void onDrag(float degrees, float offset);

    void onUp();
}