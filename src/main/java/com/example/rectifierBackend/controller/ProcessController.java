package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.message.request.ProcessForm;
import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.repository.BathRepository;
import com.example.rectifierBackend.repository.ProcessRepository;
import com.example.rectifierBackend.service.RectifierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;

@RequestMapping("/process")
@RestController
@CrossOrigin
public class ProcessController {

    ProcessRepository processRepository;
    BathRepository bathRepository;
    RectifierService rectifierService;

    public ProcessController(ProcessRepository processRepository,
                             BathRepository bathRepository,
                             RectifierService rectifierService) {
        this.processRepository = processRepository;
        this.bathRepository = bathRepository;
        this.rectifierService = rectifierService;
    }

    @GetMapping("{processId}")
    ResponseEntity<?> getOne(@PathVariable long processId) {
        Process process = processRepository
                .findById(processId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Process not found.")
                );
        return ResponseEntity.ok(process);
    }

    @DeleteMapping("{processId}")
    ResponseEntity<?> delete(@PathVariable long processId) {
        processRepository.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    ResponseEntity<?> add(@RequestBody Process process) {
        processRepository.save(process);
        return ResponseEntity.ok(process);
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(processRepository.findAll());
    }

    @PostMapping("/start")
    ResponseEntity<?> startProcess(@RequestBody ProcessForm processForm) {
        User user = User.getCurrentUser().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
        );
        Bath bath = bathRepository
                .findById(processForm.getBathId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bath not found.")
                );
        if(bath.getUser() == null || bath.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bath not occupied by current user");
        }
        Process process = new Process();
        process.setBath(bath);
        process.setDescription(processForm.getDescription());
        process.setOperator(user);
        processRepository.save(process);
        rectifierService.startProcess(process.getId());
        return ResponseEntity.ok(process);
    }

    @PostMapping("/{processId}/stop")
    ResponseEntity<?> stopProcess(@PathVariable long processId) {
        User user = User.getCurrentUser().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
        );
        Process process = processRepository
                .findById(processId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Process not found.")
                );
        if(process.getOperator() == null || process.getOperator().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Process not started by current user");
        }
        rectifierService.stopProcess(processId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/testStream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> testStream() {
        StreamingResponseBody responseBody = (OutputStream stream) -> {
            rectifierService.writeSamples(stream, null);
        };
        return ResponseEntity.ok(responseBody);
    }

}
