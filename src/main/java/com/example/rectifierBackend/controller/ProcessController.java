package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/process")
@RestController
public class ProcessController {
    @Autowired
    ProcessRepository processRepository;

    @RequestMapping(value = "{processId}", method = RequestMethod.GET)
    ResponseEntity getOne(@PathVariable long processId) {
        Process process = processRepository.findById(processId);
        if(process == null) {
            return new ResponseEntity<String>("Process not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Process>(process, HttpStatus.OK);
    }

    @RequestMapping(value = "{processId}", method = RequestMethod.DELETE)
    ResponseEntity delete(@PathVariable long processId) {
        processRepository.deleteById(processId);
        return new ResponseEntity<Bath>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    ResponseEntity add(@RequestBody Process process) {
        processRepository.save(process);
        return new ResponseEntity<Process>(process, HttpStatus.OK);
    }

}
