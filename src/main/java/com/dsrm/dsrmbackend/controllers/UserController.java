package com.dsrm.dsrmbackend.controllers;



import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", consumes ="application/json")
    void addUser(@RequestBody UserRequestDTO userRequestDTO) {}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}", produces ="application/json", consumes ="application/json")
    ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws JSONException {
        JSONObject outputObj = new JSONObject();
        outputObj.put("email",null);
        outputObj.put("name",null);
        outputObj.put("surname",null);
        outputObj.put("roles",null);
        outputObj.put("isBanned",null);
        outputObj.put("banEndDate",null);
        return null;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Page<UserDTO>> readUsers(Pageable pageable) {
        return new ResponseEntity<>(Page.empty(),HttpStatus.OK);
    }
}
