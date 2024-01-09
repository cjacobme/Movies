package cj.software.hierarchy.movie.entity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cj.software.movie")
@Validated
public class ConfigurationHolder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Valid
    private RelationalWorldConfiguration relationalWorld;

    public RelationalWorldConfiguration getRelationalWorld() {
        return relationalWorld;
    }

    public void setRelationalWorld(RelationalWorldConfiguration relationalWorld) {
        this.relationalWorld = relationalWorld;
    }
}
