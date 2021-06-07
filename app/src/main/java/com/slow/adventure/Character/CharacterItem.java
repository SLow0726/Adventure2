package com.slow.adventure.Character;

public class CharacterItem {

    private final String name;
    private final int image;

    public CharacterItem(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getCharacterName() {

        return name;
    }

    public int getCharacterImage() {

        return image;
    }


}
