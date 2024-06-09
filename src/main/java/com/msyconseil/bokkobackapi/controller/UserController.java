package com.msyconseil.bokkobackapi.controller;

import java.util.List;
import java.util.Map;

import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msyconseil.bokkobackapi.dto.UserDTO;
import com.msyconseil.bokkobackapi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PostMapping("/login")
    public CustomAnswer<UserDTO> login(@RequestBody UserDTO userDTO) throws Exception {
        return userService.login(userDTO);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/logout")
    public CustomAnswer<Boolean> logout(@RequestHeader Map<String, String> headers) throws ErrorException {
        return userService.logout(headers);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PostMapping("/")
    public CustomAnswer<UserDTO> add(@RequestHeader Map<String, String> headers, @RequestBody UserDTO userDTO) throws ErrorException {
        return userService.add(headers, userDTO);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PutMapping("/")
    public CustomAnswer<UserDTO> update(@RequestHeader Map<String, String> headers, @RequestBody UserDTO userDTO, @RequestParam String email) throws ErrorException {
        return userService.update(headers, userDTO, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/")
    public CustomAnswer<UserDTO> delete(@RequestHeader Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return userService.delete(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/all")
    public CustomListAnswer<List<UserDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam String page, @RequestParam String size) throws ErrorException {
        return userService.getAll(headers, Integer.parseInt(page), Integer.parseInt(size));
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/")
    public CustomAnswer<UserDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return userService.get(headers, email);
    }

}
