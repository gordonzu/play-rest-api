package com.gordonyx.domain

import play.api.libs.json.{Format, Json}

case class Car(id: Long, brand: String, model: String, cc: Int, sales: Long = 0) {
    def toDTO(): CarDTO = { CarDTO(Some(id), brand, model, cc, sales) }
}

object Car {
    def fromDTO(carDTO: CarDTO): Car = Car(carDTO.id.getOrElse(0L), carDTO.brand, carDTO.model, carDTO.cc, carDTO.sales) 
}

case class CarDTO(id: Option[Long], brand: String, model: String, cc: Int, sales: Long = 0) {
    def toCar(): Car = {
        Car(id.getOrElse(0L), brand, model, cc, sales)
    }
}

object CarDTO {
    implicit val format: Format[CarDTO] = Json.format[CarDTO]
    def fromCar(car: Car): CarDTO = {
        CarDTO(Some(car.id), car.brand, car.model, car.cc, car.sales)
    }
}



