package hackathon.data.fetch.base;

import hackathon.data.fetch.DataFetchApplication;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={DataFetchApplication.class})
@WebAppConfiguration
public class AbstractDataFetchTest {

}
