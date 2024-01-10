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
    private NameGenerator nameGenerator;

    public void generateParticipants() throws Exception {
        actorRepository.deleteAndRestoreIndices(this::generateActors);
    }

    private void generateActors() {
        long numExistingActors = actorRepository.getNumActors();
        RelationalWorldConfiguration relationalWorld = configurationHolder.getRelationalWorld();
        int numActors = relationalWorld.getNumActors();
        int blockSize = relationalWorld.getActorGenerationBlockSize();
        Collection<Actor> newActors = new ArrayList<>();
        if (numExistingActors < numActors) {
            int numToBeBuild = numActors - (int) numExistingActors;
            logger.info("%d actors have to be built", numToBeBuild);
            int count = 0;
            for (int iActor = 0; iActor < numToBeBuild; iActor++) {
                Actor actor = nameGenerator.generateActor();
                newActors.add(actor);
                count++;
                if (count >= blockSize) {
                    logger.info("now persist %d...", count);
                    actorRepository.generateManyActors(newActors);
                    newActors.clear();
                    count = 0;
                }
            }
            if (count > 0) {
                logger.info("now persist remaining %d...", count);
                actorRepository.generateManyActors(newActors);
            }
        }
    }
}
