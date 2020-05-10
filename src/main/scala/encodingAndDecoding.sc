import io.circe.generic.JsonCodec
import io.circe.syntax._

val intsJson = List(1, 2, 3).asJson

// Use the .as syntax for decoding data from Json

intsJson.as[List[Int]] // Right(List(1, 2, 3))

/*
The decode function from the included [parser] module
can be used to directly decode a JSON String
 */

import io.circe.parser.decode

val decodeList = decode[List[Int]]("[1, 2, 3]")
// Right(List(1, 2, 3))

// SEMI-AUTOMATIC DERIVATION

/*
Sometimes it's convenient to have an Encoder or Decoder defined in your code,
and semi-automatic derivation can help.
 */

import io.circe._
import io.circe.generic.semiauto._

case class Foo(a: Int, b: String, c: Boolean)
implicit val fooDecoder: Decoder[Foo] = deriveDecoder
implicit val fooEncoder: Encoder[Foo] = deriveEncoder

Foo(10, "string", true).asJson

/*
The circe-generic projects includes a @JsonCodec annotation that
simplifies the use of semi-automatic generic derivation
*/

/*
@JsonCodec case class Bar(i: Int, s: String)

Bar(10, "string").asJson


(doesnt run in worksheet)
 */


// FORPRODUCTN HELPER METHODS


/*
It's also possible to construct encoders and decoders for case class-like types
in a relatively boilerplate-free way without generic derivation:
*/

case class User(id: Long, firstName: String, lastName: String)

object UserCodec {
  implicit val decodeUser: Decoder[User] =
    Decoder.forProduct3("id", "first_name", "last_name")(User.apply)

  implicit val encodeUser: Encoder[User] =
    Encoder.forProduct3("id", "first_name", "last_name")(u =>
      (u.id, u.firstName, u.lastName))
}

// AUTOMATIC DERIVATION

import io.circe.generic.auto._

case class Person(name: String)

case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

val greetingJson = Greeting("Hey", Person("Chris"), 3).asJson

greetingJson.hcursor.downField("person").downField("name").as[String]

// Right("Chris")