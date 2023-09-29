package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

    private int gameId;

    @NotEmpty(message = "Please enter the name of game")
    private String nameOfGame;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotEmpty(message = "Please enter owner name")
    private String ownerName;

    private boolean currentGame=true;

    public Game() {
    }

    public Game(String nameOfGame, String ownerName) {
        this.nameOfGame = nameOfGame;
        this.startDate = LocalDate.now();
        this.ownerName = ownerName;
    }

    public Game(String nameOfGame, LocalDate startDate, LocalDate endDate, String ownerName) {
        this.nameOfGame = nameOfGame;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerName = ownerName;
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
                ", currentGame=" + currentGame +
                '}';
    }

}
