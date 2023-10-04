package com.jobgun.jobetl.domain

import zio.json.{JsonDecoder, DeriveJsonDecoder, jsonField}
import zio.Chunk

final case class JobListings(
  @jsonField("JobListing") jobListings: Chunk[JobListing]
)

object JobListings {
  implicit val jsonDecoder: JsonDecoder[JobListings] = DeriveJsonDecoder.gen
}

final case class GetResponse(@jsonField("Get") get: JobListings)

object GetResponse {
  implicit val jsonDecoder: JsonDecoder[GetResponse] = DeriveJsonDecoder.gen
}

final case class DataResponse(data: GetResponse)

object DataResponse {
  implicit val jsonDecoder: JsonDecoder[DataResponse] = DeriveJsonDecoder.gen
}

final case class FindJobsResponse(result: DataResponse) {
    def jobListings: Chunk[JobListing] = result.data.get.jobListings
}

object FindJobsResponse {
  implicit val jsonDecoder: JsonDecoder[FindJobsResponse] = DeriveJsonDecoder.gen
}