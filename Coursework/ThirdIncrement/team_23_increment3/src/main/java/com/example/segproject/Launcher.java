package com.example.segproject;

/**
 * Launcher class that exists as a target for Maven.
 * <p>
 * Maven can't use the App class as a target for compiling
 * so we use this class instead. Nothing should be added to
 * this class.
 */
public abstract class Launcher {

    public static void main(String[] args) {
        App.main(args);
    }

}
