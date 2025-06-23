package digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class CensusResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("Census")
    @Valid
    private List<Census> census = null;

    public CensusResponse addCensusItem(Census censusItem){
        if(this.census == null){
            this.census = new ArrayList<>();
        }
        this.census.add(censusItem);
        return this;
    }
}
