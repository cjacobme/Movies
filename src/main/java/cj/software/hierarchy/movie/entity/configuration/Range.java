package cj.software.hierarchy.movie.entity.configuration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Range implements Serializable {
    @NotNull
    @Min(1)
    private Integer min;

    @NotNull
    @Min(2)
    private Integer max;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public String toString() {
        String result = String.format("[%d,%d]", min, max);
        return result;
    }
}
