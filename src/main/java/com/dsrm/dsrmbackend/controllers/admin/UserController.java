package com.dsrm.dsrmbackend.controllers.admin;

import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.dto.UserRolesOnlyDTO;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController("AdminUserController")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", consumes= MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.addUser(userRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/users/{id}")
    ResponseEntity<UserDTO> getUser(@PathVariable String id){
        Optional<User> user = userService.getUser(id);
        return  ResponseEntity.of((user.map(userMapper::toUserDTO)));
    }

    @GetMapping(value = "/users")
    @PageableAsQueryParam
    ResponseEntity<Page<UserDTO>> readUsers(@Parameter(hidden = true) Pageable pageable,@RequestParam(required = false,defaultValue = "false") boolean isPending) {
        Page<User> userPage = userService.getUsers(pageable,isPending);
        return new ResponseEntity<>(userPage.map(userMapper::toUserDTO),HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/users/{id}/roles")
    ResponseEntity<Void> partialUpdateUser(@PathVariable String id, @RequestBody UserRolesOnlyDTO userRolesOnlyDTO){
        Optional<User> user =  userService.updateUser(userRolesOnlyDTO, id);
        if(user.isPresent())
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}
