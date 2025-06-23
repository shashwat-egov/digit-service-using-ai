package digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CensusSearchCriteria {
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("areaCodes")
    private List<String> areaCodes;
}
