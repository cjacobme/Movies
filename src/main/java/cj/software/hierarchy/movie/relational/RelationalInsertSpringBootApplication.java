package cj.software.hierarchy.movie.relational;

import cj.software.hierarchy.movie.relational.controller.AddRandomEntriesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cj.software.hierarchy.movie")
@SuppressWarnings("java:S923") // I'm the entry point from the operating system and need varargs
public class RelationalInsertSpringBootApplication implements CommandLineRunner {
    @Autowired
    private AddRandomEntriesController addRandomEntriesController;

    public static void main(String[] args) {
        SpringApplication.run(RelationalInsertSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) {
        addRandomEntriesController.findTom();
    }
}
