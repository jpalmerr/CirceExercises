import cats.syntax.either._
import java.time.Instant

import io.circe.{Decoder, Encoder}

// a codec for java.time.Instant might look like this:

implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)

implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str =>
  Either.catchNonFatal(Instant.parse(str)).leftMap(t => "Instant")
}

import io.circe._
import io.circe.syntax._

case class Foo(value: String)


implicit val fooKeyEncoder = new KeyEncoder[Foo] {
  override def apply(foo: Foo): String = foo.value
}

val map = Map[Foo, Int](
  Foo("hello") -> 123,
  Foo("world") -> 456
)


val json = map.asJson

implicit val fooKeyDecoder = new KeyDecoder[Foo] {
  override def apply(key: String): Option[Foo] = Some(Foo(key))
}

json.as[Map[Foo, Int]]

json.hcursor.downField("hello").as[Int]

