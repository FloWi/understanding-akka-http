package de.flwi.understandingAkkaHttp

import akka.http.scaladsl.model.headers.{ModeledCustomHeader, ModeledCustomHeaderCompanion}

import scala.util.Try


object `X-Rumba-Date` extends ModeledCustomHeaderCompanion[`X-Rumba-Date`] {
  def renderInRequests: Boolean = false

  def renderInResponses: Boolean = false

  override val name = "X-Rumba-Date"

  override def parse(value: String): Try[`X-Rumba-Date`] = {
    Try(new `X-Rumba-Date`(value))
  }
}

final class `X-Rumba-Date`(rumbaDate: String) extends ModeledCustomHeader[`X-Rumba-Date`] {
  def renderInRequests: Boolean = false

  def renderInResponses: Boolean = false

  override val companion = `X-Rumba-Date`

  override def value: String = rumbaDate

  def maybeDateValue: Option[String] = Try(value.replace(s"${`X-Rumba-Date`.name}:", "").trim).toOption
}

final class ApiTokenHeader(token: String) extends ModeledCustomHeader[ApiTokenHeader] {
  def renderInRequests = false

  def renderInResponses = false

  override val companion = ApiTokenHeader

  override def value: String = token
}

object ApiTokenHeader extends ModeledCustomHeaderCompanion[ApiTokenHeader] {
  def renderInRequests = false

  def renderInResponses = false

  override val name = "apiKey"

  override def parse(value: String) = Try(new ApiTokenHeader(value))
}
