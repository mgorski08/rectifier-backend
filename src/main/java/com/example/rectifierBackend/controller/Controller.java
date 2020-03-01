package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.rectifier.RectifierService;
import com.example.rectifierBackend.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wanna")
@RestController
public class Controller {

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    RectifierService rectifierService;

    @RequestMapping(value = "{bathId}/start", method = RequestMethod.POST)
    ResponseEntity<Process> startProcess(@PathVariable long bathId) {
        Process process = RectifierService.startProcess(bathId);
        processRepository.save(process);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }
}
