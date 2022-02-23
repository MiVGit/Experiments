package ru.rGame;

import ru.rGame.enums.Directions;
import ru.rGame.map.MapLevel;

public interface Movable {
    void move(MapLevel map, Directions direction);
}
