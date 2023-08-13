package epn.mov.bakery.model.sqlManager

import SqlObjectManager
import epn.mov.bakery.model.Bakery

object SqlBakeryManager: SqlObjectManager<String, Bakery>(Bakery::class.java){

}
