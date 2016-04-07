package de.flwi.understandingAkkaHttp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object Main extends App with Config with SimpleService {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  Http().bindAndHandle(route, httpInterface, httpPort)
}
