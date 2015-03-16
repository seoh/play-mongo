package controllers

import com.mongodb.casbah.Imports._

object Database  {
  private val Key = "Key"
  private val Value = "Value"

  val client = MongoClient("localhost", 27017)
  val db = client("test")
  val coll = db("test")

  def list = coll.find().toList
  def find(key: String) =
        coll.findOne(MongoDBObject(Key->key))

  def update(key: String, value: String) =
        coll.update(MongoDBObject(Key->key), 
                    MongoDBObject(Key->key, Value->value),
                    upsert = true).getN

  def drop = db.dropDatabase
}
