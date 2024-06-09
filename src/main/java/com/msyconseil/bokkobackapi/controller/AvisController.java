package com.msyconseil.bokkobackapi.controller;

import com.azure.core.annotation.Put;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.AvisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.msyconseil.bokkobackapi.dto.AvisDTO;
import com.msyconseil.bokkobackapi.service.AvisService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class AvisController {

    @Autowired
    AvisService avisService;

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PostMapping("/")
    public CustomAnswer<AvisDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody AvisDTO avisDTO) throws ErrorException {
        return avisService.add(headers, avisDTO);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/")
    public CustomAnswer<AvisDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return avisService.get(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/all")
    public CustomListAnswer<List<AvisDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException {
        return avisService.getAll(headers, page, size);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/allbyuser")
    public CustomListAnswer<List<AvisDTO>> getAllByUser(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return avisService.getAllByUser(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/allbybooking")
    public CustomListAnswer<List<AvisDTO>> getAllByReservation(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int idReservation) throws ErrorException {
        return avisService.getAllByReservation(headers, email, idReservation);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PutMapping("/")
    public CustomAnswer<AvisDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody AvisDTO avisDTO, @RequestParam String email) throws ErrorException {
        return avisService.update(headers, avisDTO, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/")
    public CustomAnswer<AvisDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return avisService.delete(headers, email);
    }


}
