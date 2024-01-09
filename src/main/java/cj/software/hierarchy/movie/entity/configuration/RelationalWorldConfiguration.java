package cj.software.hierarchy.movie.entity.configuration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

public class RelationalWorldConfiguration implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(1)
    private Integer numActors;

    @NotNull
    @Min(1)
    private Integer numMovies;

    @NotNull
    @Min(1)
    private Integer rolesPerMovie;

    @NotNull
    @Valid
    private Range actorGivenName;

    @NotNull
    @Valid
    private Range actorFamilyName;

    @NotNull
    @Min(1)
    private Integer minMovieNameLength;

    @NotNull
    @Min(1)
    private Integer maxMovieNameLength;

    @NotNull
    @Min(1)
    private Integer minRoleNameLength;

    @NotNull
    @Min(1)
    private Integer maxRoleNameLength;

    public Integer getNumActors() {
        return numActors;
    }

    public void setNumActors(Integer numActors) {
        this.numActors = numActors;
    }

    public Integer getNumMovies() {
        return numMovies;
    }

    public void setNumMovies(Integer numMovies) {
        this.numMovies = numMovies;
    }

    public Integer getRolesPerMovie() {
        return rolesPerMovie;
    }

    public void setRolesPerMovie(Integer rolesPerMovie) {
        this.rolesPerMovie = rolesPerMovie;
    }

    public Range getActorGivenName() {
        return actorGivenName;
    }

    public void setActorGivenName(Range actorGivenName) {
        this.actorGivenName = actorGivenName;
    }

    public Range getActorFamilyName() {
        return actorFamilyName;
    }

    public void setActorFamilyName(Range actorFamilyName) {
        this.actorFamilyName = actorFamilyName;
    }

    public Integer getMinMovieNameLength() {
        return minMovieNameLength;
    }

    public void setMinMovieNameLength(Integer minMovieNameLength) {
        this.minMovieNameLength = minMovieNameLength;
    }

    public Integer getMaxMovieNameLength() {
        return maxMovieNameLength;
    }

    public void setMaxMovieNameLength(Integer maxMovieNameLength) {
        this.maxMovieNameLength = maxMovieNameLength;
    }

    public Integer getMinRoleNameLength() {
        return minRoleNameLength;
    }

    public void setMinRoleNameLength(Integer minRoleNameLength) {
        this.minRoleNameLength = minRoleNameLength;
    }

    public Integer getMaxRoleNameLength() {
        return maxRoleNameLength;
    }

    public void setMaxRoleNameLength(Integer maxRoleNameLength) {
        this.maxRoleNameLength = maxRoleNameLength;
    }
}
