package com.msyconseil.bokkobackapi.controller;

import java.util.List;
import java.util.Map;

import com.azure.core.annotation.Put;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.ProfilException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msyconseil.bokkobackapi.dto.ProfilDTO;
import com.msyconseil.bokkobackapi.service.ProfilService;
import com.msyconseil.bokkobackapi.dto.ProfilRegisterDTO;

@RestController
@RequestMapping("/profil")
public class ProfilController {

    @Autowired
    ProfilService profilService;

    @PostMapping("/")
    public CustomAnswer<ProfilDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody ProfilDTO profilDTO) throws ErrorException {
        return profilService.add(headers, profilDTO);
    }

    @PutMapping("/")
    public CustomAnswer<ProfilDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody ProfilDTO profilDTO, @RequestParam String email) throws ErrorException {
        return profilService.update(headers, profilDTO, email);
    }

    @DeleteMapping("/")
    public CustomAnswer<ProfilDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return profilService.delete(headers, email);
    }

    @GetMapping("/")
    public CustomAnswer<ProfilDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return profilService.get(headers, email);
    }

    @GetMapping("/all")
    public CustomListAnswer<List<ProfilDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, int size) throws ErrorException {
        return profilService.getAll(headers, page, size);
    }

    @PostMapping("/register")
    public CustomAnswer<ProfilRegisterDTO> register(@RequestBody ProfilRegisterDTO profilRegisterDTO) {
        return profilService.register(profilRegisterDTO);
    }

}
