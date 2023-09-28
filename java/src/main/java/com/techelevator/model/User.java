package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.*;

public class User {
   private int id;
   private String username;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private boolean activated;
   @NotEmpty
   private String role;
   private String firstName;
   private String lastName;
   private Set<Authority> authorities = new HashSet<>();
   private List<Stock> listOfOwnedStocks;
   private List<Game> listOfGames;
   private List<Transaction> listOfTransaction = new ArrayList<>();
   private BigDecimal profileBalance;

   public User() {
   }

   public User(int id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      if(authorities != null) this.setAuthorities(authorities);
      this.activated = true;
      this.listOfOwnedStocks = new ArrayList<>();
      this.listOfGames = new ArrayList<>();
      this.profileBalance = new BigDecimal(0);
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public List<Stock> getListOfOwnedStocks() {
      return listOfOwnedStocks;
   }

   public void setListOfOwnedStocks(List<Stock> listOfOwnedStocks) {
      this.listOfOwnedStocks = listOfOwnedStocks;
   }

   public void addOwnedStocks(Stock stock) {
      this.listOfOwnedStocks.add(stock);
   }

   public List<Game> getListOfGames() {
      return listOfGames;
   }

   public void setListOfGames(List<Game> listOfGames) {
      this.listOfGames = listOfGames;
   }

   public void addOwnedGames(Game game) {
      this.listOfGames.add(game);
   }

   public List<Transaction> getListOfTransaction() {
      return listOfTransaction;
   }

   public void setListOfTransaction(List<Transaction> listOfTransaction) {
      this.listOfTransaction = listOfTransaction;
   }

   public void addToListOfTransaction(Transaction transaction) {
      this.listOfTransaction.add(transaction);
   }

   public BigDecimal getProfileBalance() {
      return profileBalance;
   }

   public void setProfileBalance(BigDecimal profileBalance) {
      this.profileBalance = profileBalance;
   }

   public void addToProfileBalance(BigDecimal balance) {
      this.profileBalance = this.profileBalance.add(balance);
   }

   public void subtractFromProfileBalance(BigDecimal balance) {
      this.profileBalance = this.profileBalance.subtract(balance);
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for(String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
         this.authorities.add(new Authority(authority));
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
