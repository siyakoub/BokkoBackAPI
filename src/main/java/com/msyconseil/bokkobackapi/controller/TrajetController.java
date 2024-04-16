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

    @PostMapping("/")
    public CustomAnswer<TrajetDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody TrajetDTO trajetDTO) throws ErrorException {
        return trajetService.add(headers, trajetDTO);
    }

    @GetMapping("/byid")
    public CustomAnswer<TrajetDTO> getById(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int idTrajet) throws ErrorException {
        return trajetService.getById(headers, email, idTrajet);
    }

    @GetMapping("/all")
    public CustomListAnswer<List<TrajetDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException {
        return trajetService.getAll(headers, page, size);
    }

    @GetMapping("/allbydriver")
    public CustomListAnswer<List<TrajetDTO>> getAllByDriver(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return trajetService.getAllByDriver(headers, email);
    }

    @PutMapping("/")
    public CustomAnswer<TrajetDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody TrajetDTO trajetDTO, @RequestParam String email) throws ErrorException {
        return trajetService.update(headers, trajetDTO, email);
    }

    @GetMapping("/")
    public CustomAnswer<TrajetDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return trajetService.get(headers, email);
    }

    @DeleteMapping("/")
    public CustomAnswer<TrajetDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return trajetService.delete(headers, email);
    }

    @DeleteMapping("/byid")
    public CustomAnswer<TrajetDTO> deleteById(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int id) throws ErrorException {
        return trajetService.deleteById(headers, email, id);
    }


}
