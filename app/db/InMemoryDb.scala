package com.gordonyx.db 

import scala.collection.mutable.ListBuffer
import scala.util.Random  
import com.gordonyx.domain.Car

object InMemoryDb extends Db[Car, Long, ListBuffer]{

    override val db: ListBuffer[Car] = ListBuffer.empty
    val idGenerator = Random

    def insert(car: Car): Car = {
        val idx = db.indexWhere(x => (x.id == car.id))
        val newCar = car.copy(id=idGenerator.nextLong())
        if(idx > -1) throw new RuntimeException(s"Duplicate element witgh is: ${car.id}")
        else db.addOne(newCar)
        newCar 
    }

    def update(car: Car): Unit = {
         val idx = db.indexWhere(x => (x.id == car.id))
         if (idx > -1) db.update(idx, car)
         else throw new RuntimeException(s"No element to update with id: ${car.id}")
    }

    def delete(id: Long): Unit = {
        val idx = db.indexWhere(x => (x.id == id))
        if (idx > -1) db.remove(idx)
        else throw new RuntimeException(s"No elementr to delete with id: ${id}")
    }

    def find(id: Long): Option[Car] = db.find(x => (x.id == id))
    def findAll(): List[Car] = db.toList 
    def findOrderedBySales(): List[Car] = db.sortBy(x => (x.sales)).toList 

}

