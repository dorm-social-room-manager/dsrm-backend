package com.dsrm.dsrmbackend.Controllers;



import com.dsrm.dsrmbackend.DTOobjects.UserDTO;
import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.services.UserService;
import com.dsrm.dsrmbackend.tables.User;
import com.dsrm.dsrmbackend.tablesrepo.UserRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", consumes ="application/json")
    User addUser(@RequestBody UserDTO userDTO) {
        return userService.getUserMapper().toUser(userDTO);
    }

    @GetMapping(value = "/users/{id}", produces ="application/json", consumes ="application/json")
    ResponseEntity<Object> oneUser(@PathVariable Long id) throws JSONException {
        JSONObject outputObj = new JSONObject();
        outputObj.put("email",null);
        outputObj.put("name",null);
        outputObj.put("surname",null);
        outputObj.put("roles",null);
        outputObj.put("isBanned",null);
        outputObj.put("banEndDate",null);

        return new ResponseEntity(outputObj,HttpStatus.OK);
    }
    @GetMapping(value = "/users")
    public ResponseEntity readBooks (@RequestParam  Pageable pageable,@RequestParam Long pageNum) {
        return ResponseEntity.ok(userService.readUsers(pageable));
    }
}
