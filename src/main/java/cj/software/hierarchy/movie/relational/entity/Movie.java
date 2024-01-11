package cj.software.hierarchy.movie.relational.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "movie",
        indexes = {
                @Index(name = "idxMovieTitle", columnList = "title")
        })
@SequenceGenerator(name = "genMovie", sequenceName = "seq_movie", allocationSize = 1)
@DynamicUpdate
@DynamicInsert
public class Movie implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "genMovie")
    private Long id;

    @Version
    @Column(name = "jpa_version", nullable = false)
    private int version;

    @NotBlank
    @Column(name = "title", nullable = false, updatable = false, unique = true)
    private final String title;

    @Column(name = "roles_added", nullable = false)
    private boolean rolesAdded;

    Movie() {
        // default constructor needed for JPA
        title = "default";
    }

    public Movie(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRolesAdded() {
        return rolesAdded;
    }

    public void setRolesAdded(boolean rolesAdded) {
        this.rolesAdded = rolesAdded;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("title", title);
        String result = builder.build();
        return result;
    }
}
