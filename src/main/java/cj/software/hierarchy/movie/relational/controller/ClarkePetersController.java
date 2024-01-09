package cj.software.hierarchy.movie.relational.controller;

import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Movie;
import org.springframework.stereotype.Controller;

@Controller
public class ClarkePetersController extends ControllerBase {
    public void createParticipants() {
        Actor meg = searchOrCreateActor("Meg", "Ryan"); // Meg Ryan has Kevin Bocon number 2
        Movie courage = searchOrCreateMovie("Courage under Fire");
        searchOrCreateRole("Emma Walden", meg, courage);
        Actor denzel = searchOrCreateActor("Denzel", "Washington");
        searchOrCreateRole("Nathan Serling", denzel, courage); // Denzel Washington gets number 3
        Movie flight2012 = searchOrCreateMovie("Flight (2012)");
        searchOrCreateRole("William Whitaker", denzel, flight2012);
        Actor bruce = searchOrCreateActor("Bruce", "Greenwood");
        searchOrCreateRole("Charlie Anderson", bruce, flight2012);  // Bruce Greenwood gets number 4
        Movie challenger = searchOrCreateMovie("The Challenger");
        searchOrCreateRole("General Donald Kutyna", bruce, challenger);
        Actor william = searchOrCreateActor("William", "Hurt");
        searchOrCreateRole("Richard Feynman", william, challenger); // William Hurt gets number 5
        Movie endgame = searchOrCreateMovie("Endgame");
        searchOrCreateRole("Prof. Willie Esterhuyse", william, endgame);
        Actor clarke = searchOrCreateActor("Clarke", "Peters");
        searchOrCreateRole("Nelson Mandela", clarke, endgame);      // Clark Peters gets number 6
    }
}
