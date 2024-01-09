package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.entity.configuration.ConfigurationHolder;
import cj.software.hierarchy.movie.entity.configuration.RelationalWorldConfiguration;
import cj.software.hierarchy.movie.relational.dao.ActorRepository;
import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.util.NameGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ManyDataController extends ControllerBase {
    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private ConfigurationHolder configurationHolder;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private NameGenerator nameGenerator;

    public void generateParticipants() {
        generateActors();
    }

    private void generateActors() {
        long numExistingActors = actorRepository.getNumActors();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numActors = relationalWorld.getNumActors();
        if (numExistingActors < numActors) {
            int numToBeBuild = numActors - (int) numExistingActors;
            logger.info("%d actors have to be built", numToBeBuild);
            for (int iActor = 0; iActor < numToBeBuild; iActor++) {
                Actor actor = nameGenerator.generateActor();
                super.searchOrCreateActor(actor.getGivenName(), actor.getFamilyName());
            }
        }
    }
}
