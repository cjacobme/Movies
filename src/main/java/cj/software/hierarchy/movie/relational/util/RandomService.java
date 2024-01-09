package cj.software.hierarchy.movie.relational.util;

import cj.software.hierarchy.movie.spring.TraceAtLogLevel;
import org.apache.logging.log4j.spi.StandardLevel;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@TraceAtLogLevel(level = StandardLevel.DEBUG)
public class RandomService {

    private final SecureRandom secureRandom = new SecureRandom();

    public int getRandomBetween(int min, int max) {
        int result = secureRandom.nextInt(min, max);
        return result;
    }

    public int getRandom(int max) {
        int result = secureRandom.nextInt(max);
        return result;
    }
}
