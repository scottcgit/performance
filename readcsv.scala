import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ryvus extends Simulation {

	val httpProtocol = http
		.baseURL("http://yoursite.com.au")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("en-US,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")
	
	val csvfeeder = csv("logs.csv").random
		
	val scn = scenario("performance")
		.feed(csvfeeder)
		.exec(http("request_0")
			.get("${URL}")
			.headers(headers_0))

	setUp(scn.inject(rampUsersPerSec(1) to(1) during(1 minutes) randomized).protocols(httpProtocol))
}