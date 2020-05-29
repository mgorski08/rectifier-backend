package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Client;
import com.example.rectifierBackend.repository.ClientRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;

@RequestMapping("/report")
@RestController
@CrossOrigin
public class ReportController {

    ClientRepository clientRepository;

    public ReportController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(value = "testReport/{a}", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<StreamingResponseBody> testReport(@PathVariable String a) {
        StreamingResponseBody responseBody = (OutputStream outputStream) -> {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph(a));
            document.close();
        };
        return ResponseEntity.ok(responseBody);
    }

}
