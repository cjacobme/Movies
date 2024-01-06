package cj.software.hierarchy.movie.relational.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "actor")
@SequenceGenerator(name = "genActor", sequenceName = "seq_actor", allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Actor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "genActor")
    private Long id;

    @Version
    @Column(name = "jpa_version", nullable = false)
    private int version;

    @NotBlank
    @Column(name = "given_name", nullable = false)
    private String givenName;

    @NotBlank
    @Column(name = "family_name", nullable = false)
    private String familyName;

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("given", givenName)
                .append("family", familyName);
        String result = builder.build();
        return result;
    }
}
