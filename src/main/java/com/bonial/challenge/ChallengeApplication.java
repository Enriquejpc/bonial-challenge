package com.bonial.challenge;

import com.bonial.challenge.adapter.file.JsonFileAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    JsonFileAdapter jsonFileAdapter;
    private final String JSON_DATA = "data/locations_big.json";

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        jsonFileAdapter.bulkData(JSON_DATA);
    }
}
