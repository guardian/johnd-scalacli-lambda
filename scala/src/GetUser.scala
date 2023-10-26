
import sttp.client4.quick.*
import sttp.client4.Response
import upickle.default.*

@main
def testIt(): Unit = {
  println("respnse is: " + GetUser.response())
}

object GetUser {

  case class UserResponse(date_of_birth: String) derives ReadWriter

  def response(): UserResponse =
    val res = quickRequest
      .get(uri"https://random-data-api.com/api/v2/users")
      .send()
    read[UserResponse](res.body)

}
