package linear_search

fun <T>Array<T>.linearSearch(value : T) : Int{
    for ((i, el) in this.withIndex()){
        if (el == value) return i
    }

    return -1
}