package cj.software.hierarchy.movie.relational.util;

import cj.software.hierarchy.movie.entity.configuration.ConfigurationHolder;
import cj.software.hierarchy.movie.entity.configuration.Range;
import cj.software.hierarchy.movie.entity.configuration.RelationalWorldConfiguration;
import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.spring.Trace;
import com.github.javafaker.service.FakeValuesService;
import org.apache.commons.lang3.StringUtils;
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
    public Actor generateActor() {
        RelationalWorldConfiguration relationalWorldConfiguration = configurationHolder.getRelationalWorld();
        Range givenNameRange = relationalWorldConfiguration.getActorGivenName();
        Range familyNameRange = relationalWorldConfiguration.getActorFamilyName();
        String givenName = generate(givenNameRange);
        String familyName = generate(familyNameRange);
        Actor result = new Actor();
        result.setGivenName(givenName);
        result.setFamilyName(familyName);
        return result;
    }

    private String generate(Range range) {
        int min = range.getMin();
        int max = range.getMax();
        int length = randomService.getRandomBetween(min, max);
        String generated = generate(length, 1);
        String result = StringUtils.capitalize(generated);
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
