package fr.gangoffourt.challengemobe.minigame;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Const {
    public static ArrayList<String> SCORES = new ArrayList<>();

    public static void addScore(@NotNull String s) {
        SCORES.add("Essai " + (SCORES.size()+1) + " : " + s);
    }
}
