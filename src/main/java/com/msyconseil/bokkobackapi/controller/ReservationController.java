package com.msyconseil.bokkobackapi.controller;

import java.util.List;
import java.util.Map;

import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.ReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msyconseil.bokkobackapi.dto.ReservationDTO;
import com.msyconseil.bokkobackapi.service.ReservationService;

@RestController
@RequestMapping("/booking")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PostMapping("/")
    public CustomAnswer<ReservationDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody ReservationDTO reservationDTO) throws ErrorException {
        return reservationService.add(headers, reservationDTO);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/bypassager")
    public CustomListAnswer<List<ReservationDTO>> getAllByUser(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException{
        return reservationService.getAllByUser(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @PutMapping("/")
    public CustomAnswer<ReservationDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody ReservationDTO parameter, @RequestParam String email) throws ErrorException {
        return reservationService.update(headers, parameter, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/")
    public CustomAnswer<ReservationDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return reservationService.delete(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/all")
    public CustomListAnswer<List<ReservationDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException {
        return reservationService.getAll(headers, page, size);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/")
    public CustomAnswer<ReservationDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return reservationService.get(headers, email);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @DeleteMapping("/byid")
    public CustomAnswer<ReservationDTO> deleteById(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int idReservation) throws ErrorException {
        return reservationService.deleteById(headers, email, idReservation);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/allbytrajet")
    public CustomListAnswer<List<ReservationDTO>> getAllByTrajet(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int idTrajet) throws ErrorException {
        return reservationService.getAllByTrajet(headers, email, idTrajet);
    }

    @CrossOrigin(origins = {"https://app.bokyon-app.com", "*"})
    @GetMapping("/allbytrajetinprogress")
    public CustomListAnswer<List<ReservationDTO>> getAllByTrajetInProgress(@RequestHeader final Map<String, String> headers, @RequestParam String email, @RequestParam int idTrajet) throws ErrorException {
        return reservationService.getAllByTrajetInProgress(headers, email, idTrajet);
    }

}
