package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.entity.configuration.ConfigurationHolder;
import cj.software.hierarchy.movie.entity.configuration.RelationalWorldConfiguration;
import cj.software.hierarchy.movie.relational.dao.ActorRepository;
import cj.software.hierarchy.movie.relational.dao.MovieRepository;
import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Movie;
import cj.software.hierarchy.movie.relational.util.NameGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class ManyDataController extends ControllerBase {
    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private ConfigurationHolder configurationHolder;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private NameGenerator nameGenerator;

    public void generateParticipants() throws Exception {
        actorRepository.deleteAndRestoreIndices(this::generateActors);
        movieRepository.deleteAndRestoreIndices(this::generateMovies);
    }

    private void generateActors() {
        long numExistingActors = actorRepository.getNumActors();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numActors = relationalWorld.getNumActors();
        if (numExistingActors < numActors) {
            int blockSize = relationalWorld.getActorGenerationBlockSize();
            Collection<Actor> newActors = new ArrayList<>();
            int numToBeBuild = numActors - (int) numExistingActors;
            logger.info("%d actors have to be built", numToBeBuild);
            int count = 0;
            for (int iActor = 0; iActor < numToBeBuild; iActor++) {
                Actor actor = nameGenerator.generateActor();
                newActors.add(actor);
                count++;
                if (count >= blockSize) {
                    logger.info("now persist %d actors...", count);
                    actorRepository.generateManyActors(newActors);
                    newActors.clear();
                    count = 0;
                }
            }
            if (count > 0) {
                logger.info("now persist remaining %d actors...", count);
                actorRepository.generateManyActors(newActors);
            }
        }
    }

    private void generateMovies() {
        long numExistingMovies = movieRepository.getNumMovies();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numMovies = relationalWorld.getNumMovies();
        if (numExistingMovies < numMovies) {
            int blockSize = relationalWorld.getMovieGenerationBlockSize();
            Collection<Movie> newMovies = new ArrayList<>();
            int numToBeBuilt = numMovies - (int) numExistingMovies;
            logger.info("%d movies have to be built", numToBeBuilt);
            int count = 0;
            for (int iMovie = 0; iMovie < numToBeBuilt; iMovie++) {
                String title = nameGenerator.generateMovieTitle();
                Movie movie = new Movie();
                movie.setTitle(title);
                newMovies.add(movie);
                count++;
                if (count >= blockSize) {
                    logger.info("now persist %d movies...", count);
                    movieRepository.generateManyMovies(newMovies);
                    newMovies.clear();
                    count = 0;
                }
            }
            if (count > 0) {
                logger.info("now persis remaining %d movies...", count);
                movieRepository.generateManyMovies(newMovies);
            }
        }
    }
}
