package cj.software.hierarchy.movie.relational.util;

import cj.software.hierarchy.movie.entity.configuration.ConfigurationHolder;
import cj.software.hierarchy.movie.entity.configuration.RelationalWorldConfiguration;
import cj.software.hierarchy.movie.spring.Trace;
import com.github.javafaker.service.FakeValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NameGenerator {
    @Autowired
    private ConfigurationHolder configurationHolder;

    @Autowired
    private RandomService randomService;

    @Autowired
    private FakeValuesService fakeValuesService;

    @Trace
    public String generateActorName() {
        RelationalWorldConfiguration relationalWorldConfiguration = configurationHolder.getRelationalWorld();
        int min = relationalWorldConfiguration.getMinActorNameLength();
        int max = relationalWorldConfiguration.getMaxActorNameLength();
        int length = randomService.getRandomBetween(min, max);
        String result = generate(length, 2);
        return result;
    }

    @Trace
    public String generateMovieTitle() {
        RelationalWorldConfiguration relationalWorldConfiguration = configurationHolder.getRelationalWorld();
        int min = relationalWorldConfiguration.getMinMovieNameLength();
        int max = relationalWorldConfiguration.getMaxMovieNameLength();
        int length = randomService.getRandomBetween(min, max);
        int numWords = randomService.getRandomBetween(2, 7);
        String result = generate(length, numWords);
        return result;
    }

    private String generate(int length, int numWords) {
        String pattern = generatePattern(length, numWords);
        String result = fakeValuesService.letterify(pattern);
        return result;
    }

    private String generatePattern(int length, int numWords) {
        StringBuilder sb = new StringBuilder();
        sb.append("?".repeat(Math.max(0, length)));
        for (int iWord = 0; iWord < numWords - 1; iWord++) {
            int position = randomService.getRandom(length);
            sb.setCharAt(position, ' ');
        }
        String result = sb.toString();
        return result;
    }
}
