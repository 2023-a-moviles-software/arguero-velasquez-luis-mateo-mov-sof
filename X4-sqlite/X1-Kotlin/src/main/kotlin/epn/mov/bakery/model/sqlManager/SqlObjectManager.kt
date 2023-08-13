import jakarta.persistence.Persistence

open class SqlObjectManager<K,V>(
    private var clazz: Class<V>
){

    private val entityManager = managerFactory.createEntityManager()
    fun read(key:K):V?{
        return entityManager.find(clazz,key)
    }

    fun update(entity:V){
        val t = entityManager.transaction
        t.begin()
        entityManager.merge(entity)
        t.commit()

    }

    fun create(entity:V){
        val t = entityManager.transaction
        t.begin()
        entityManager.persist(entity)
        t.commit()
    }

    fun delete(entity: V){
        val t = entityManager.transaction
        t.begin()
        entityManager.remove(entityManager)
        t.commit()
    }

    companion object{
        private val managerFactory = Persistence.createEntityManagerFactory("sqlitedb")
    }
}