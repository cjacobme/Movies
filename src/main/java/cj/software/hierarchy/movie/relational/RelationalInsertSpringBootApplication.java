package cj.software.hierarchy.movie.relational;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings("java:S923") // I'm the entry point from the operating system and need varargs
public class RelationalInsertSpringBootApplication implements CommandLineRunner {

    public static void main (String[] args) {
        SpringApplication.run(RelationalInsertSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // to be implemented
    }
}
