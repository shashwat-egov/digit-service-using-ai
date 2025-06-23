package digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Population distribution by demographic variable")
public class PopulationByDemographic {
    @JsonProperty("demographicVariable")
    private String demographicVariable;

    @JsonProperty("populationDistribution")
    private Object populationDistribution;
}
