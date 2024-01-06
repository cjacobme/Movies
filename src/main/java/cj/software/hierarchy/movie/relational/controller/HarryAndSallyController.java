package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Movie;
import org.springframework.stereotype.Controller;

@Controller
public class HarryAndSallyController extends ControllerBase {
    public void createParticipants() {
        Actor meg = searchOrCreateActor("Meg", "Ryan");
        Actor billy = searchOrCreateActor("Billy", "Crystal");
        Movie movie = searchOrCreateMovie("Harry and Sally");
        searchOrCreateRole("Harry Burns", billy, movie);
        searchOrCreateRole("Sally Albright", meg, movie);
    }
}
