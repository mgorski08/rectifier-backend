package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.message.response.ResponseMessage;
import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.repository.ProcessRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/process")
@RestController
@CrossOrigin
public class ProcessController {

    ProcessRepository processRepository;

    public ProcessController(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(processRepository.findAll());
    }

    @GetMapping("{processId}")
    ResponseEntity<?> getOne(@PathVariable long processId) {
        Process process = processRepository.findById(processId);
        if(process == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Process not found."));
        }
        return ResponseEntity.ok(process);
    }

    @DeleteMapping("{processId}")
    ResponseEntity<?> delete(@PathVariable long processId) {
        processRepository.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("add")
    ResponseEntity<?> add(@RequestBody Process process) {
        processRepository.save(process);
        return ResponseEntity.ok(process);
    }

}
