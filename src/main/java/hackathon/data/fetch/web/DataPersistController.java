package hackathon.data.fetch.web;


import hackathon.data.fetch.api.dto.UserReceipeRatingDto;
import hackathon.data.fetch.elasticsearch.service.IndexDataService;
import hackathon.data.fetch.elasticsearch.service.QueryDataService;

import org.apache.tomcat.util.http.parser.MediaTypeCache;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/data-fetch")
public class DataPersistController {

	@Autowired
	IndexDataService iService;
	
	@Autowired
	QueryDataService qService;
	
	@RequestMapping(value="/user", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Boolean persistUserReceipeRating(@RequestBody UserReceipeRatingDto urr){
		if (urr.getReceipeId()==null) {
			Long id = qService.queryForId(urr.getExternalId());
			if (id!=null) {
				urr.setUserId(id);
			}
			
		}
		return (iService.index(urr));
	}
	
	@RequestMapping(value="/resolve-external", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String resolveId(@RequestParam(value="id",required=true)Long id) {
		return qService.queryForExternal(id);

	}
	
}
