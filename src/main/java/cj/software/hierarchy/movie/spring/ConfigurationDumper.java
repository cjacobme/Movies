package cj.software.hierarchy.movie.spring;

import cj.software.hierarchy.movie.entity.configuration.ConfigurationHolder;
import cj.software.hierarchy.movie.entity.configuration.RelationalWorldConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.StandardLevel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@TraceAtLogLevel(level = StandardLevel.DEBUG)
public class ConfigurationDumper implements InitializingBean {
    private static final String RANGE_FORMAT = "[%d,%d]";

    public static final String STRING_FORMAT = "%50.50s = %s";

    public static final String INT_FORMAT = "%50.50s = %d";

    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private ConfigurationHolder configurationHolder;

    @Override
    public void afterPropertiesSet() {
        logger.info("##############################################################################");
        logger.info("#####                                                                    #####");
        logger.info("#####          R E L A T I O N A L   W O R L D                           #####");
        log(configurationHolder.getRelationalWorld());
        logger.info("##############################################################################");
    }

    private void log(RelationalWorldConfiguration configuration) {
        if (logger.isInfoEnabled()) {
            logger.info(INT_FORMAT, "number of actors", configuration.getNumActors());
            logger.info(INT_FORMAT, "number of movies", configuration.getNumMovies());
            logger.info(INT_FORMAT, "min number of roles / movie", configuration.getMinRolesPerMovie());
            logger.info(INT_FORMAT, "max number of roles / movie", configuration.getMaxRolesPerMovie());
            logger.info(STRING_FORMAT, "actor given name length", configuration.getActorGivenName());
            logger.info(STRING_FORMAT, "actor family name length", configuration.getActorFamilyName());
            logger.info(INT_FORMAT, "block size for actor generation", configuration.getActorGenerationBlockSize());
            logger.info(STRING_FORMAT, "movie name length",
                    String.format(RANGE_FORMAT, configuration.getMinMovieNameLength(), configuration.getMaxMovieNameLength()));
            logger.info(STRING_FORMAT, "role name length",
                    String.format(RANGE_FORMAT, configuration.getMinRoleNameLength(), configuration.getMaxRoleNameLength()));
            logger.info(INT_FORMAT, "block size for movie generation", configuration.getMovieGenerationBlockSize());
        }
    }
}
