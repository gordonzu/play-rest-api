package com.gordonyx.db

trait Db[A, B, C[_]] {
    val db: C[A]
    def insert(entity: A): A
    def find(id: B): Option[A]
    def update(entity: A): Unit
    def delete(id: B): Unit
    def findAll(): List[A]
    def findOrderedBySales(): List[A]
}


