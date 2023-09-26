package com.techelevator.dao.ModelTest;

import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User sut;
    @Before
    public void setup(){
        this.sut = new User(1, "user1", "user1", "ROLE_USER");
    }



}
