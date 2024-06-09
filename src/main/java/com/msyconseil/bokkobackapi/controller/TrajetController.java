package com.msyconseil.bokkobackapi.controller;

import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.TrajetException;
import com.sun.jna.WString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msyconseil.bokkobackapi.dto.TrajetDTO;
import com.msyconseil.bokkobackapi.service.TrajetService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journey")
public class TrajetController {

    @Autowired
    TrajetService trajetService;

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PostMapping("/")
    public CustomAnswer<TrajetDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody TrajetDTO trajetDTO) throws ErrorException {
        return trajetService.add(headers, trajetDTO);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/tobecome")
    public CustomListAnswer<List<TrajetDTO>> getAllTrajetToBecome(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return trajetService.getAllToBecome(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/byid")
    public CustomAnswer<TrajetDTO> getById(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int idTrajet) throws ErrorException {
        return trajetService.getById(headers, email, idTrajet);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/all")
    public CustomListAnswer<List<TrajetDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException {
        return trajetService.getAll(headers, page, size);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/allbydriver")
    public CustomListAnswer<List<TrajetDTO>> getAllByDriver(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return trajetService.getAllByDriver(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PutMapping("/")
    public CustomAnswer<TrajetDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody TrajetDTO trajetDTO, @RequestParam String email) throws ErrorException {
        return trajetService.update(headers, trajetDTO, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/")
    public CustomAnswer<TrajetDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return trajetService.get(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/")
    public CustomAnswer<TrajetDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return trajetService.delete(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/byid")
    public CustomAnswer<TrajetDTO> deleteById(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int id) throws ErrorException {
        return trajetService.deleteById(headers, email, id);
    }


}
