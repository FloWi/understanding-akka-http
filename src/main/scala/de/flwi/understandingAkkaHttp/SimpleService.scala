package de.flwi.understandingAkkaHttp

import akka.http.scaladsl.model.headers.`User-Agent`
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

trait SimpleService {

  case class UA(userAgent: String)

  case class Bid(bid: String)

  case class RumbaDate(date: String)

  val allNecessaryArguments: Directive[(Option[Bid], Option[UA], Option[RumbaDate])] = optionalCookie("bid").flatMap { maybeBidCookie =>
    optionalHeaderValueByType[`User-Agent`]().flatMap { maybeUserAgent =>
      optionalHeaderValueByType[`X-Rumba-Date`]().flatMap { mbr =>
        tprovide(
          (
            maybeBidCookie.map(bid => Bid(bid.value)),
            maybeUserAgent.map(ua => UA(ua.value)),
            mbr.flatMap(_.maybeDateValue.map(RumbaDate))
          )
        )
      }
    }
  }

  def route: Route = {
    post {
      pathPrefix("foo") {
        allNecessaryArguments { (bid, userAgent, rumbaDate) =>
          complete(s"Bid: $bid, UserAgent: $userAgent, RumbaDate: $rumbaDate")
        }
      }
    }
  }
}
