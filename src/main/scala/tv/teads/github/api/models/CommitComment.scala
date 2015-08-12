package tv.teads.github.api.models

import org.joda.time.DateTime
import play.api.data.mapping._
import play.api.libs.json.{ JsObject, JsValue }

trait CommitCommentFormats {
  self: UserFormats ⇒
  implicit lazy val commitCommentJsonWrite: Write[CommitComment, JsValue] = {
    import play.api.data.mapping.json.Writes._
    import tv.teads.github.api.util.CustomWrites._
    Write.gen[CommitComment, JsObject]
  }

  implicit lazy val commitCommentJsonRead = From[JsValue] { __ ⇒
    import play.api.data.mapping.json.Rules._
    import tv.teads.github.api.util.CustomRules._
    // let's no leak implicits everywhere
    (
      (__ \ "url").read[String] ~
      (__ \ "html_url").read[String] ~
      (__ \ "id").read[Long] ~
      (__ \ "user").read[User] ~
      (__ \ "position").read[Option[Long]] ~
      (__ \ "line").read[Option[Long]] ~
      (__ \ "path").read[Option[String]] ~
      (__ \ "commit_id").read[String] ~
      (__ \ "created_at").read(jodaLongOrISO) ~
      (__ \ "updated_at").read(jodaLongOrISO) ~
      (__ \ "body").read[String]
    )(CommitComment.apply _)
  }
}
case class CommitComment(
  url:        String,
  html_url:   String,
  id:         Long,
  user:       User,
  position:   Option[Long],
  line:       Option[Long],
  path:       Option[String],
  commit_id:  String,
  created_at: DateTime,
  updated_at: DateTime,
  body:       String
)
