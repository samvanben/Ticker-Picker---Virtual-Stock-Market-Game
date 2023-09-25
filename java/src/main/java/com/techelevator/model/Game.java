package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Game {
    @NotEmpty
    private int gameId;
    @NotEmpty
    private String nameOfGame;
    @NotEmpty
    private LocalDate startDate;
    @NotEmpty
    private LocalDate endDate;
    @NotEmpty
    private String ownerName;
    @NotEmpty
    private List[] listOfPlayers;
    @NotEmpty
    private boolean currentGame = true;
    @NotEmpty
    private int daysOfPlaying;
    @NotEmpty
    private String currentGameBoard;
    public Game() {
    }

    public Game(int gameId, String nameOfGame, LocalDate startDate, LocalDate endDate, String ownerName, List[] listOfPlayers, boolean currentGame, int daysOfPlaying, String currentGameBoard) {
        this.gameId = gameId;
        this.nameOfGame = nameOfGame;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerName = ownerName;
        this.listOfPlayers = listOfPlayers;
        this.currentGame = currentGame;
        this.daysOfPlaying = daysOfPlaying;
        this.currentGameBoard = currentGameBoard;
    }

    public Game(String nameOfGame, LocalDate startDate, LocalDate endDate, String ownerName, List[] listOfPlayers, boolean currentGame, int daysOfPlaying, String currentGameBoard) {
        this.nameOfGame = nameOfGame;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerName = ownerName;
        this.listOfPlayers = listOfPlayers;
        this.currentGame = currentGame;
        this.daysOfPlaying = daysOfPlaying;
        this.currentGameBoard = currentGameBoard;
    }

    public int getDaysOfPlaying() {
        return daysOfPlaying;
    }

    public void setDaysOfPlaying(int daysOfPlaying) {
        this.daysOfPlaying = daysOfPlaying;
    }

    public String getCurrentGameBoard() {
        return currentGameBoard;
    }

    public void setCurrentGameBoard(String currentGameBoard) {
        this.currentGameBoard = currentGameBoard;
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

    public List[] getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(List[] listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
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
                ", listOfPlayers=" + Arrays.toString(listOfPlayers) +
                ", currentGame=" + currentGame +
                ", daysOfPlaying=" + daysOfPlaying +
                ", currentGameBoard='" + currentGameBoard + '\'' +
                '}';
    }
}
