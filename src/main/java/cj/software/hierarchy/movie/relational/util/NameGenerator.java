package cj.software.hierarchy.movie.relational.util;

import cj.software.hierarchy.movie.entity.configuration.ConfigurationHolder;
import cj.software.hierarchy.movie.entity.configuration.Range;
import cj.software.hierarchy.movie.entity.configuration.RelationalWorldConfiguration;
import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.spring.Trace;
import cj.software.hierarchy.movie.spring.TraceAtLogLevel;
import com.github.javafaker.service.FakeValuesService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.spi.StandardLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@TraceAtLogLevel(level = StandardLevel.DEBUG)
public class NameGenerator {
    @Autowired
    private ConfigurationHolder configurationHolder;

    @Autowired
    private RandomService randomService;

    @Autowired
    private FakeValuesService fakeValuesService;

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

    public String generateRoleName() {
        RelationalWorldConfiguration relationalWorldConfiguration = configurationHolder.getRelationalWorld();
        Range givenNameRange = relationalWorldConfiguration.getActorGivenName();
        Range familyNameRange = relationalWorldConfiguration.getActorFamilyName();
        String givenName = generate(givenNameRange);
        String familyName = generate(familyNameRange);
        String result = givenName + " " + familyName;
        return result;
    }

    private String generate(Range range) {
        int min = range.getMin();
        int max = range.getMax();
        int length = randomService.getRandomBetween(min, max);
        String generated = generate(length);
        String result = StringUtils.capitalize(generated);
        return result;
    }

    @Trace
    public String generateMovieTitle() {
        RelationalWorldConfiguration relationalWorldConfiguration = configurationHolder.getRelationalWorld();
        int min = relationalWorldConfiguration.getMinMovieNameLength();
        int max = relationalWorldConfiguration.getMaxMovieNameLength();
        int length = randomService.getRandomBetween(min, max);
        String first = generate(length);
        StringBuilder sb = new StringBuilder(first);
        int numWords = randomService.getRandomBetween(2, 7);
        for (int iWord = 1; iWord < numWords; iWord++) {
            length = randomService.getRandomBetween(min, max);
            String word = generate(length);
            sb.append(" ").append(word);
        }
        String result = sb.toString();
        return result;
    }

    private String generate(int length) {
        String pattern = generatePattern(length);
        String result = fakeValuesService.letterify(pattern);
        return result;
    }

    private String generatePattern(int length) {
        StringBuilder sb = new StringBuilder();
        sb.append("?".repeat(Math.max(0, length)));
        String result = sb.toString();
        return result;
    }
}
