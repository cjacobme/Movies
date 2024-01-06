package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Movie;
import org.springframework.stereotype.Controller;

@Controller
public class SleeplessInSeattleController extends ControllerBase {

    public void createAllParticipants() {
        Actor tom = searchOrCreateActor("Tom", "Hanks");
        Actor meg = searchOrCreateActor("Meg", "Ryan");
        Movie sleepless = searchOrCreateMovie("Sleepless in Seattle");
        searchOrCreateRole("Sam Baldwin", tom, sleepless);
        searchOrCreateRole("Annie Reed", meg, sleepless);
    }
}
