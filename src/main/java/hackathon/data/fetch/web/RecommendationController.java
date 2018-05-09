package hackathon.data.fetch.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import hackathon.data.fetch.elasticsearch.service.QueryDataService;

@RestController
@RequestMapping(value = "/recommendation")
public class RecommendationController {

	@Autowired
	QueryDataService qService;

	@Value("${application.recommendation-engine-url}")
	String recommendationEngineUrl;

	@RequestMapping(value = "/user/recommend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getRecommendationsforUser(
			@RequestParam(required = true, value = "userId") Long userId) {
		List<String> results = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();

		Long[] recoms = restTemplate.getForObject(recommendationEngineUrl
				+ "/receipe?userId=" + userId + "&nor=10", Long[].class);

		for (Long itemId : recoms) {
			results.add(qService.queryForExternal(itemId));
		}

		return results;
	}

	@RequestMapping(value = "/item/recommend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getRecommendationsForItem(
			@RequestParam(required = true, value = "itemId") String recipeId) {

		List<String> results = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();

		Long selectedItemId = qService.queryForId(recipeId);
		Long[] recoms = restTemplate.getForObject(recommendationEngineUrl
				+ "/similarItems?itemId=" + selectedItemId + "&nor=10",
				Long[].class);

		if (recoms != null) {

			for (Long itemId : recoms) {
				results.add(qService.queryForExternal(itemId));
			}
		}

		return results;
	}
}
