package com.msquared.dataintensive.model

import java.util.Date

case class NYTArticleRow(
                          web_url: Option[String],
                          snippet: Option[String],
                          lead_paragraph: Option[String],
                          abstra: Option[String],
                          print_page: Option[String],
                          source: Option[String],
                          pub_date: Option[Date],
                          document_type: Option[String],
                          news_desk: Option[String],
                          section_name: Option[String],
                          subsection_name: Option[String],
                          type_of_material: Option[String],
                          word_count: Option[String]
                        )