package epn.mov.bakery.model

import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType

@Table(name = "BreadCollection")
@Entity
class BreadCollection() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @OneToMany
    @Cascade(CascadeType.ALL)
    var breads: MutableList<Bread> = mutableListOf()

    constructor(breads: MutableList<Bread>) : this(){
        this.breads = breads
    }

    fun count():Int{
        return breads.map { it.stock }.stream().mapToInt { it }.sum()
    }

    override fun toString(): String {
        return breads.toString()
    }
}