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

    @PostMapping("/")
    public CustomAnswer<ReservationDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody ReservationDTO reservationDTO) throws ErrorException {
        return reservationService.add(headers, reservationDTO);
    }

    @PutMapping("/")
    public CustomAnswer<ReservationDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody ReservationDTO parameter, @RequestParam String email) throws ErrorException {
        return reservationService.update(headers, parameter, email);
    }

    @DeleteMapping("/")
    public CustomAnswer<ReservationDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return reservationService.delete(headers, email);
    }

    @GetMapping("/all")
    public CustomListAnswer<List<ReservationDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException {
        return reservationService.getAll(headers, page, size);
    }

    @GetMapping("/")
    public CustomAnswer<ReservationDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return reservationService.get(headers, email);
    }


}
