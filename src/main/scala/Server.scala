import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Source, Sink}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import scala.concurrent.Future
import scala.concurrent.duration._

class Server extends Runnable {

	def run() = {

		implicit val system = ActorSystem("server")
		implicit val materializer = ActorMaterializer()

		val serverSource = Http().bind(interface = "localhost", port = 8200)

		val requestHandler: HttpRequest => HttpResponse = {
			case HttpRequest(GET, Uri.Path("/stream"), _, _, _) =>
				HttpResponse(entity = HttpEntity.Chunked(ContentTypes.`text/plain`, Source(0 seconds, 1 seconds, "test")))
		}

		val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach { connection =>
			connection handleWithSyncHandler requestHandler
		}).run()

	}

}