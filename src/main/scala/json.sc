import io.circe.Json
val jsonString: Json = Json.fromString("scala exercises")

val jsonDouble: Option[Json] = Json.fromDouble(1)

val jsonBoolean: Json = Json.fromBoolean(true)

val fieldList = List(
  ("key1", Json.fromString("value1")),
  ("key2", Json.fromInt(1)))

val jsonFromFields: Json = Json.fromFields(fieldList)

jsonFromFields.noSpaces // String = {"key1":"value1","key2":1}
jsonFromFields.spaces2

/*
val res1: String =
{
"key1" : "value1",
"key2" : 1
}
*/

Json.obj("key" -> Json.fromString("value"))

Json.fromFields(
  List(
    ("name", Json.fromString("sample json")),
    ("data", Json.obj("done" -> Json.fromBoolean(false)))
  )).noSpaces

/*
{"name":"sample json","data":{"done":false}}
 */

Json.fromValues(List(Json.obj("x" -> Json.fromInt(1)))).noSpaces

// [{"x":1}]

val jsonArray: Json = Json.fromValues(List(
  Json.fromFields(List(("field1", Json.fromInt(1)))),
  Json.fromFields(List(
    ("field1", Json.fromInt(200)),
    ("field2", Json.fromString("Having circe in Scala Exercises is awesome"))))))

def transformJson(jsonArray: Json): Json =
  jsonArray mapArray { oneJson =>
    oneJson.init
  }

transformJson(jsonArray) // "[{"field1" : 1 }]
