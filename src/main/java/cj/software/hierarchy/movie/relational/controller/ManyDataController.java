package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.entity.configuration.ConfigurationHolder;
import cj.software.hierarchy.movie.entity.configuration.RelationalWorldConfiguration;
import cj.software.hierarchy.movie.relational.dao.ActorRepository;
import cj.software.hierarchy.movie.relational.dao.MovieRepository;
import cj.software.hierarchy.movie.relational.dao.RoleRepository;
import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Movie;
import cj.software.hierarchy.movie.relational.entity.Role;
import cj.software.hierarchy.movie.relational.util.NameGenerator;
import cj.software.hierarchy.movie.relational.util.RandomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RandomService randomService;

    public void generateParticipants(Actor lastActor) throws Exception {
        generateActors();
        generateMovies();
        generateRoles(lastActor);
    }

    private void generateActors() throws Exception {
        long numExistingActors = actorRepository.getNumActors();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numActors = relationalWorld.getNumActors();
        if (numExistingActors < numActors) {
            actorRepository.deleteAndRestoreIndices(this::generateMissingActors);
        }
    }

    private void generateMissingActors() {
        long numExistingActors = actorRepository.getNumActors();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numActors = relationalWorld.getNumActors();
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

    private void generateMovies() throws Exception {
        long numExistingMovies = movieRepository.getNumMovies();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numMovies = relationalWorld.getNumMovies();
        if (numExistingMovies < numMovies) {
            movieRepository.deleteAndRestoreIndices(this::generateMissingMovies);
        }
    }

    private void generateMissingMovies() {
        long numExistingMovies = movieRepository.getNumMovies();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numMovies = relationalWorld.getNumMovies();
        int blockSize = relationalWorld.getMovieGenerationBlockSize();
        Collection<Movie> newMovies = new ArrayList<>();
        int numToBeBuilt = numMovies - (int) numExistingMovies;
        logger.info("%d movies have to be built", numToBeBuilt);
        int count = 0;
        for (int iMovie = 0; iMovie < numToBeBuilt; iMovie++) {
            String title = nameGenerator.generateMovieTitle();
            Movie movie = new Movie(title);
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

    private record ActorIdRange(long lastActorId, int numActors) {

    }

    private void generateRoles(Actor lastActor) {
        int numActors = (int) actorRepository.getNumActors();
        ActorIdRange actorIdRange = new ActorIdRange(lastActor.getId(), numActors);
        movieRepository.iterateAllRolesNotAdded(this::checkRolesOfMovie, actorIdRange);
    }

    private Actor selectRandomActor(ActorIdRange actorIdRange) {
        int numActors = actorIdRange.numActors();
        long lastActorId = actorIdRange.lastActorId();
        int actorIndex = randomService.getRandom(numActors);
        Actor result = actorRepository.selectRandomActor(actorIndex);
        while (result.getId() <= lastActorId) {
            logger.debug("Actor %s may not be used", result);
            actorIndex = randomService.getRandom(numActors);
            result = actorRepository.selectRandomActor(actorIndex);
        }
        return result;
    }

    private void checkRolesOfMovie(Movie movie, ActorIdRange actorIdRange) {
        long numExistingRoles = roleRepository.determineNumRolesForMovie(movie);
        RelationalWorldConfiguration realationalWorld = configurationHolder.getRelationalWorld();
        int min = realationalWorld.getMinRolesPerMovie();
        if (numExistingRoles < min) {
            int max = realationalWorld.getMaxRolesPerMovie();
            int numRequiredRoles = randomService.getRandomBetween(min, max);
            int numToBeBuilt = numRequiredRoles - (int) numExistingRoles;
            List<Role> newRoles = new ArrayList<>();
            for (int iRole = 0; iRole < numToBeBuilt; iRole++) {
                String name = nameGenerator.generateRoleName();
                Actor actor = selectRandomActor(actorIdRange);
                Role role = new Role();
                role.setName(name);
                role.setMovie(movie);
                role.setActor(actor);
                newRoles.add(role);
            }
            roleRepository.saveManyRoles(movie, newRoles);
        }
    }
}
