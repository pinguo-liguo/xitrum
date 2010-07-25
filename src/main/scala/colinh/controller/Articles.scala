package colinh.controller

import org.jboss.netty.handler.codec.http.HttpResponseStatus
import colinh.model.Article

class Articles extends Application {
  def index {
    at("articles", Article.all)
    render
  }

  def show {
    val id = param("id").get.toLong
    Article.first(id) match {
      case None =>
        response.setStatus(HttpResponseStatus.NOT_FOUND)
        render("Errors#error404")

      case Some(article) =>
        at("article", article)
        render
    }
  }

  def make {
    val article = new Article
    at("article", article)
    render
  }

  def create {
    val article = new Article
    article.title  = param("title").get
    article.teaser = param("teaser").get
    article.body   = param("body").get

    at("article", article)
    render("make")
  }

  def edit {

  }
}
