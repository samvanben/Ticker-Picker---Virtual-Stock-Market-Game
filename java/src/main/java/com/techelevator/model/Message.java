package com.techelevator.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private int messageId;
    private int gameId;
    private int userId;
    private String messageBody;
    private String messageDateAndTime;

    public Message() {
    }

    public Message(int gameId, int userId, String messageBody) {
        this.gameId = gameId;
        this.userId = userId;
        this.messageBody = messageBody;
        this.messageDateAndTime = dateAndTime();
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageDateAndTime() {
        return messageDateAndTime;
    }

    public void setMessageDateAndTime(String messageDateAndTime) {
        this.messageDateAndTime = messageDateAndTime;
    }

    private String dateAndTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", gameId=" + gameId +
                ", userId=" + userId +
                ", messageBody='" + messageBody + '\'' +
                ", messageDateAndTime='" + messageDateAndTime + '\'' +
                '}';
    }
}
