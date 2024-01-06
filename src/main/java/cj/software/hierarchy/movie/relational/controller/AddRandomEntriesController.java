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

    public void createActorsForSleepless() {
        Actor tom = searchOrCreateActor("Tom", "Hanks");
        Actor meg = searchOrCreateActor("Meg", "Ryan");
    }

    private Actor searchOrCreateActor(String givenName, String familyName) {
        Actor found = actorRepository.findActorByNames(givenName, familyName);
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
}
