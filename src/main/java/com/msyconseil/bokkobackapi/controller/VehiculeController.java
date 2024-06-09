package com.msyconseil.bokkobackapi.controller;

import com.msyconseil.bokkobackapi.dto.VehiculeDTO;
import com.msyconseil.bokkobackapi.service.VehiculeService;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.VehiculeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicule")
public class VehiculeController {

    @Autowired
    VehiculeService vehiculeService;

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PostMapping("/")
    public CustomAnswer<VehiculeDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody VehiculeDTO vehiculeDTO) throws ErrorException {
        return vehiculeService.add(headers, vehiculeDTO);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/withid")
    public CustomAnswer<VehiculeDTO> getById(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int id) throws ErrorException {
        return vehiculeService.getById(headers, email, id);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/all")
    public CustomListAnswer<List<VehiculeDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException, VehiculeException {
        return vehiculeService.getAll(headers, page, size);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PutMapping("/")
    public CustomAnswer<VehiculeDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody VehiculeDTO vehiculeDTO, @RequestParam String email) throws ErrorException {
        return vehiculeService.update(headers, vehiculeDTO, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/bydriver")
    public CustomListAnswer<List<VehiculeDTO>> getByUser(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException, VehiculeException {
        return vehiculeService.getAllByUser(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/")
    public CustomAnswer<VehiculeDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return vehiculeService.get(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/")
    public CustomAnswer<VehiculeDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException, VehiculeException {
        return vehiculeService.delete(headers, email);
    }

}
