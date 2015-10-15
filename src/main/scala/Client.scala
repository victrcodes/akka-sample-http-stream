import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{Uri, HttpRequest}
import akka.stream.ActorMaterializer

object Client extends App {

	new Thread(new Server).start()

	implicit val system = ActorSystem("client")
	import system.dispatcher

	implicit val materializer = ActorMaterializer()

	val source = Uri("http://localhost:8200/stream")
	val stream = Http().singleRequest(HttpRequest(uri = source)).flatMap { response =>
		response.entity.dataBytes.runForeach { chunk =>
			println(chunk.utf8String)
		}
	}

}