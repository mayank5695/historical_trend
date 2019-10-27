package com.msquared.dataintensive.model

import java.sql.Date

case class NYTArticleRow(
                          web_url: String,
                          snippet: Option[String],
                          lead_paragraph: Option[String],
                          abstra: Option[String],
                          print_page: Option[String],
                          source: Option[String],
                          pub_date: Date,
                          document_type: Option[String],
                          news_desk: Option[String],
                          section_name: Option[String],
                          subsection_name: Option[String],
                          type_of_material: Option[String],
                          word_count: Option[String]
                        )