package com.msyconseil.bokkobackapi.controller;

import java.util.List;
import java.util.Map;

import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msyconseil.bokkobackapi.dto.PaiementDTO;
import com.msyconseil.bokkobackapi.service.PaiementService;

@RestController
@RequestMapping("/payment")
public class PaiementController {

    @Autowired
    PaiementService paiementService;

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PostMapping("/")
    public CustomAnswer<PaiementDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody PaiementDTO paiementDTO) throws ErrorException {
        return paiementService.add(headers, paiementDTO);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PutMapping("/")
    public CustomAnswer<PaiementDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody PaiementDTO paiementDTO, @RequestParam String email) throws ErrorException {
        return paiementService.update(headers, paiementDTO, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/")
    public CustomAnswer<PaiementDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return paiementService.delete(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/")
    public CustomAnswer<PaiementDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return paiementService.get(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/all")
    public CustomListAnswer<List<PaiementDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException {
        return paiementService.getAll(headers, page, size);
    }

}
