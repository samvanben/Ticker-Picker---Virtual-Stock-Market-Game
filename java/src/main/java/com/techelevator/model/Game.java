package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {
    private int gameId;
    @NotEmpty
    private String nameOfGame;
    @NotEmpty
    private LocalDate startDate;
    private LocalDate endDate;
    @NotEmpty
    private String ownerName;
    private boolean isRealGame;
    @NotEmpty
    private boolean currentGame;
    private int daysOfPlaying;

    public Game() {
    }

    public Game(String nameOfGame, String ownerName, boolean isRealGame) {
        this.nameOfGame = nameOfGame;
        this.startDate = LocalDate.now();
        this.ownerName = ownerName;
        this.isRealGame = isRealGame;
        this.currentGame = true;
        this.daysOfPlaying = 0;
    }

    public Game(String nameOfGame, LocalDate startDate, LocalDate endDate, String ownerName,
                boolean isRealGame) {
        this.nameOfGame = nameOfGame;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerName = ownerName;
        this.isRealGame = isRealGame;
        this.currentGame = true;
    }

    public boolean isRealGame() {
        return isRealGame;
    }

    public void setRealGame(boolean realGame) {
        isRealGame = realGame;
    }

    public long getDaysOfPlaying() {
        long days = Math.abs(ChronoUnit.DAYS.between(this.startDate, this.endDate));
        return days;
    }

    public void setDaysOfPlaying(int daysOfPlaying) {
        this.daysOfPlaying = daysOfPlaying;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getNameOfGame() {
        return nameOfGame;
    }

    public void setNameOfGame(String nameOfGame) {
        this.nameOfGame = nameOfGame;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(boolean currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", nameOfGame='" + nameOfGame + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", ownerName='" + ownerName + '\'' +
                ", isRealGame=" + isRealGame +
                ", currentGame=" + currentGame +
                ", daysOfPlaying=" + daysOfPlaying +
                '}';
    }

}
