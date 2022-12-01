package com.dsrm.dsrmbackend.Controllers;



import com.dsrm.dsrmbackend.tables.User;
import com.dsrm.dsrmbackend.tablesrepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", consumes ="application/json")
    User addUser(@RequestBody User user) {
            return userRepo.save(user);
    }


}
