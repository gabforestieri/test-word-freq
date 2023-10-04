package com.wfrequency.controller;
import com.wfrequency.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class WfrequencyController {

    private final FileService fileService;

    public WfrequencyController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/process")
    public ResponseEntity<List<Object[]>> processFile(@RequestPart MultipartFile file) throws IOException {
        try {
            byte[] fileBytes = file.getBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);
            InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            Map<String, Integer> wordCounts = fileService.calculateWordFrequency(bufferedReader);

            wordCounts.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));


            List<Object[]> result = wordCounts.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                    .collect(Collectors.toList());

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

