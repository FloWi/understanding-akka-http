package de.flwi.understandingAkkaHttp

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.{`User-Agent`, HttpCookiePair, Cookie, RawHeader}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{ FunSpec, Matchers }

class MyTest extends FunSpec with Matchers with ScalatestRouteTest with SimpleService {

  describe("HeaderAndCookieExtractors") {

    val path: String = "/foo"
    it("should not fail when cookie oder header-values are missing") {

      val post: HttpRequest = Post(path)
      val out: RouteTestResult = post ~> route

      out ~> check {
        val response: String = responseAs[String]
        response shouldBe "Bid: None, UserAgent: None, RumbaDate: None"
      }
    }

    it("should parse bid-cookie correctly") {

      val post: HttpRequest = Post(path).withHeaders(Cookie(HttpCookiePair("bid", "bid1234")))
      val out: RouteTestResult = post ~> route

      out ~> check {
        val response: String = responseAs[String]
        response shouldBe "Bid: Some(Bid(bid1234)), UserAgent: None, RumbaDate: None"
      }
    }

    it("should parse UserAgent-header correctly") {

      val post: HttpRequest = Post(path).withHeaders(
        `User-Agent`.apply("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:37.0) Gecko/20100101 Firefox/37.0")
      )
      val out: RouteTestResult = post ~> route

      out ~> check {
        val response: String = responseAs[String]
        response shouldBe "Bid: None, UserAgent: Some(UA(Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:37.0) Gecko/20100101 Firefox/37.0)), RumbaDate: None"
      }
    }

    it("should parse custom-header for RumbaDate correctly") {

      val post: HttpRequest = Post(path).withHeaders(RawHeader("X-Rumba-Date", "2016-04-07T16:19:33.947Z"))
      val out: RouteTestResult = post ~> route

      out ~> check {
        val response: String = responseAs[String]
        response shouldBe "Bid: None, UserAgent: None, RumbaDate: Some(RumbaDate(2016-04-07T16:19:33.947Z))"
      }
    }
  }
}
