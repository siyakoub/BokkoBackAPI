package com.msyconseil.bokkobackapi.controller;

import java.util.List;
import java.util.Map;

import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msyconseil.bokkobackapi.dto.MessageDTO;
import com.msyconseil.bokkobackapi.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/")
    public CustomAnswer<MessageDTO> add(@RequestHeader final Map<String, String> headers, @RequestBody MessageDTO messageDTO) throws ErrorException {
        return messageService.add(headers, messageDTO);
    }

    @GetMapping("/")
    public CustomAnswer<MessageDTO> get(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return messageService.get(headers, email);
    }

    @GetMapping("/all")
    public CustomListAnswer<List<MessageDTO>> getAll(@RequestHeader final Map<String, String> headers, @RequestParam int page, @RequestParam int size) throws ErrorException{
        return messageService.getAll(headers, page, size);
    }

    @PutMapping("/")
    public CustomAnswer<MessageDTO> update(@RequestHeader final Map<String, String> headers, @RequestBody MessageDTO messageDTO, @RequestParam String email) throws ErrorException {
        return messageService.update(headers, messageDTO, email);
    }

    @DeleteMapping("/")
    public CustomAnswer<MessageDTO> delete(@RequestHeader final Map<String, String> headers, @RequestParam String email) throws ErrorException {
        return messageService.delete(headers, email);
    }

}
