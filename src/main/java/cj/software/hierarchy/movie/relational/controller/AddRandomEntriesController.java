package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.dao.ActorRepository;
import cj.software.hierarchy.movie.relational.entity.Actor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AddRandomEntriesController {

    @Autowired
    private ActorRepository actorRepository;

    private final Logger logger = LogManager.getFormatterLogger();

    public void findTom() {
        Actor actor = actorRepository.findActorByNames("Tom", "Hanks");
        logger.info("Tom Hanks has id %d", actor.getId());
    }
}
