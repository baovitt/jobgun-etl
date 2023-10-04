package com.jobgun.jobetl.domain

import zio.json.jsonField

final case class JobListing(
    created:         String,
    title:           String,
    description:     String,
    @jsonField("employment_type") employmentType: String,
    location:        String,
    url:             String,
    @jsonField("company_name") companyName:    String,
    @jsonField("company_url") companyUrl:     String,
    country:         String
)

object JobListing {
  import zio.json.{JsonDecoder, JsonEncoder, DeriveJsonDecoder, DeriveJsonEncoder}

  implicit val jsonDecoder: JsonDecoder[JobListing] = DeriveJsonDecoder.gen
  implicit val jsonEncoder: JsonEncoder[JobListing] = DeriveJsonEncoder.gen
}