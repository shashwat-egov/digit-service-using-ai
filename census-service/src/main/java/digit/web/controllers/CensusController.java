package digit.web.controllers;

import digit.service.CensusService;
import digit.util.ResponseInfoFactory;
import digit.web.models.CensusRequest;
import digit.web.models.CensusResponse;
import digit.web.models.CensusSearchCriteria;
import digit.web.models.CensusSearchRequest;
import digit.web.models.Census;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/census")
public class CensusController {

    @Autowired
    private CensusService censusService;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @PostMapping("/_create")
    public ResponseEntity<CensusResponse> create(@Valid @RequestBody CensusRequest request){
        List<Census> data = censusService.create(request);
        ResponseInfo info = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        return new ResponseEntity<>(CensusResponse.builder().census(data).responseInfo(info).build(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/_search")
    public ResponseEntity<CensusResponse> search(@Valid @RequestBody CensusSearchRequest request){
        List<Census> data = censusService.search(request.getCensusSearchCriteria());
        ResponseInfo info = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        return new ResponseEntity<>(CensusResponse.builder().census(data).responseInfo(info).build(), HttpStatus.OK);
    }

    @PostMapping("/_update")
    public ResponseEntity<CensusResponse> update(@Valid @RequestBody CensusRequest request){
        List<Census> data = censusService.update(request);
        ResponseInfo info = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        return new ResponseEntity<>(CensusResponse.builder().census(data).responseInfo(info).build(), HttpStatus.ACCEPTED);
    }
}
