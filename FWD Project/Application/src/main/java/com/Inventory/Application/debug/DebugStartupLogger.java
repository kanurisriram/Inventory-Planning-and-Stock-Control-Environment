package com.Inventory.Application.debug;

import com.Inventory.Application.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DebugStartupLogger implements ApplicationRunner {

    private final ProductRepository productRepository;

    public DebugStartupLogger(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Windows safe path
        String logPath = "debug-log.txt";

        Path path = Path.of(logPath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        String payload =
                "{"
                        + "\"message\":\"Spring Boot started successfully\","
                        + "\"repository\":\"" + productRepository.getClass().getName() + "\","
                        + "\"timestamp\":\"" + System.currentTimeMillis() + "\""
                        + "}";

        try (FileWriter fw = new FileWriter(logPath, true)) {
            fw.write(payload);
            fw.write("\n");
        }
    }
}