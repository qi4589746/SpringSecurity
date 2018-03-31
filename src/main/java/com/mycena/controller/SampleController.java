package com.mycena.controller;

import com.mycena.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jihung on 3/30/18.
 */

@RestController
@RequestMapping("/api")
public class SampleController {

    @Autowired
    SampleService sampleService;

    @RequestMapping(value = "/content")
    private ResponseEntity<String> getContent()
    {
        return sampleService.getContent();
    }

}
