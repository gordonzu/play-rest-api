package com.gordonyx.repository

import com.gordonyx.db.Db
import com.gordonyx.domain.Car 
import com.gordonyx.repository.errors.RepositoryError 
import scala.collection.mutable.ListBuffer
import scala.util.Try 

class CarRepository(val db: Db[Car, Long, ListBuffer]) {
    
    def findById(id: Long): Either[RepositoryError, Option[Car]] = handleIfErrors(db.find(id)) 
    def save(car: Car): Either[RepositoryError, Car] = handleIfErrors(db.insert(car))
    def update(car: Car): Either[RepositoryError, Unit] = handleIfErrors(db.update(car))
    def delete(id: Long): Either[RepositoryError, Unit] = handleIfErrors(db.delete(id))
    def findAll(): Either[RepositoryError, List[Car]] = handleIfErrors(db.findAll())
    def findOrderedBySales(): Either[RepositoryError, List[Car]] = handleIfErrors(db.findOrderedBySales())

    private def handleIfErrors[A](f: => A) =
        Try(f).fold(
            e => Left(RepositoryError(e.getMessage)), 
            v => Right(v)
        )
}


