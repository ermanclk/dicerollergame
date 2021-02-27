package org.familygame.domain;

public class Player {

    private String name;
    private int score=0;
    private boolean started=false;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        this.started = true;
    }

    public void backToInitialState() {
        this.started = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
