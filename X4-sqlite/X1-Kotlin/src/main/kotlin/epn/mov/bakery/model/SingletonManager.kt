package epn.mov.bakery.model

import epn.mov.bakery.model.sqlManager.SqlBakeryManager

class SingletonManager {
    companion object{
        var bakery: Bakery? = null
        fun save(){
            if(SqlBakeryManager.read(bakery!!.name) == null){
                SqlBakeryManager.create(bakery!!)
            }
            else{
                SqlBakeryManager.update(bakery!!)
            }
        }
        fun load(bakeryName:String){
            bakery = SqlBakeryManager.read(bakeryName)
        }
    }

}