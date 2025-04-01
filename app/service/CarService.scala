package com.gordonyx.service

import com.gordonyx.service.errors.ServiceError
import com.gordonyx.domain.CarDTO
import com.gordonyx.repository.CarRepository
import com.gordonyx.repository.errors.RepositoryError

trait CarService {
    def get(id: Long): Either[ServiceError, Option[CarDTO]]
    def save(car: CarDTO): Either[ServiceError, CarDTO]
    def update(car: CarDTO): Either[ServiceError, Unit]
    def delete(car: CarDTO): Either[ServiceError, Unit]
    def getAll(): Either[ServiceError, List[CarDTO]]
    def getOrderedBySales(): Either[ServiceError, List[CarDTO]]
}

class CarServiceLive(repository: CarRepository) extends CarService {

    override def get(id: Long): Either[ServiceError, Option[CarDTO]] =
        repository.findById(id)
            .fold(
                error => Left(toServiceError(error)),
                car   => Right(car.map(_.toDTO()))
            )

    override def save(carDTO: CarDTO): Either[ServiceError, CarDTO] =
        repository.save(carDTO.toCar())
            .fold(
                error => Left(toServiceError(error)), 
                car => Right(car.toDTO())
            )

    override def update(carDTO: CarDTO): Either[ServiceError, Unit] =
        repository.update(carDTO.toCar())
            .fold(
                error => Left(toServiceError(error)), 
                _ => Right(())
            )

    override def delete(carDTO: CarDTO): Either[ServiceError, Unit] =
        repository.delete(carDTO.id.getOrElse(0L))
            .fold(
                error => Left(toServiceError(error)), 
                _ => Right(())
            )

    override def getAll(): Either[ServiceError, List[CarDTO]] =
        repository.findAll()
            .fold(
                error => Left(toServiceError(error)),
                carList => Right(carList.map(_.toDTO()))
            )

    override def getOrderedBySales(): Either[ServiceError, List[CarDTO]] = 
        repository.findOrderedBySales()
            .fold(
                error => Left(toServiceError(error)),
                carList => Right(carList.map(_.toDTO()))
            )

    private def toServiceError(repositoryError: RepositoryError) =
        ServiceError(repositoryError.message)

}
