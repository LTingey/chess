package model;

import java.util.HashSet;

public record GameList(HashSet<GameData> list, String message) {
}
