package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.relational.dao.ActorRepository;
import cj.software.hierarchy.movie.relational.dao.MovieRepository;
import cj.software.hierarchy.movie.relational.dao.RoleRepository;
import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Movie;
import cj.software.hierarchy.movie.relational.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

abstract class ControllerBase {

    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoleRepository roleRepository;

    protected Actor searchOrCreateActor(String givenName, String familyName) {
        Actor found = actorRepository.searchActorByNames(givenName, familyName);
        Actor result;
        if (found != null) {
            logger.info("%s %s has id %d", givenName, familyName, found.getId());
            result = found;
        } else {
            logger.info("%s %s not found", givenName, familyName);
            Actor created = actorRepository.createActor(givenName, familyName);
            logger.info("%s %s has id %d", givenName, familyName, created.getId());
            result = created;
        }
        return result;
    }

    protected Movie searchOrCreateMovie(String title) {
        Movie result;
        Movie found = movieRepository.searchMovieByTitle(title);
        if (found != null) {
            logger.info("Movie %s has id %d", title, found.getId());
            result = found;
        } else {
            logger.info("Movie %s not found", title);
            Movie created = movieRepository.createMovie(title);
            logger.info("Movie %s has id %d", title, created.getId());
            result = created;
        }
        return result;
    }

    protected Role searchOrCreateRole(String name, Actor actor, Movie movie) {
        Role result;
        Role found = roleRepository.search(name, actor, movie);
        if (found != null) {
            logger.info("Role %s has id %d", name, found.getId());
            result = found;
        } else {
            logger.info("Role %s not found", name);
            Role created = roleRepository.createRole(name, actor, movie);
            logger.info("Role %s has id %d", name, created.getId());
            result = created;
        }
        return result;
    }
}
