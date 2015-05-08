import com.civolution.api.{DiffService}
import com.civolution.core.{CoreActors, Core}
import org.specs2.mutable.Specification
import spray.routing.Directives
import spray.testkit.Specs2RouteTest

class DiffServiceSpec extends Specification with Directives with Specs2RouteTest with Core with CoreActors {

  val diffService = new DiffService(persistActor, diffActor)
  val route = diffService.route

  "DiffService" should {

    "return a greeting for GET requests to the root path" in {
      Get("/v1/diff/5c3497db-8382-4775-b722-bf81e6a13704") ~> route ~> check {
        responseAs[String] must contain("equal")
      }
    }
  }
}
