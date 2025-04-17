package com.example.paintapi.paintmatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paint")
public class PaintmatcherApplicationController {

    public static void main(String[] args) {
        SpringApplication.run(PaintmatcherApplicationController.class, args);
    }

    @GetMapping(value = "/")
    public String index() {
        return "アクセス成功です";
    }

}
