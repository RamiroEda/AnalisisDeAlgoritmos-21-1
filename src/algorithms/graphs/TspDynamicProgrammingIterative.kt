package algorithms.graphs

import java.util.*

class TspDynamicProgrammingIterative(start: Int, distance: Array<DoubleArray>) {
    private val N: Int = distance.size
    private val start: Int
    private val distance: Array<DoubleArray>
    private val tour: MutableList<Int?> = ArrayList()
    private var minTourCost = Double.POSITIVE_INFINITY
    private var ranSolver = false

    constructor(distance: Array<DoubleArray>) : this(0, distance) {}

    // Returns the optimal tour for the traveling salesman problem.
    fun getTour(): List<Int?> {
        if (!ranSolver) solve()
        return tour
    }

    // Returns the minimal tour cost.
    val tourCost: Double
        get() {
            if (!ranSolver) solve()
            return minTourCost
        }

    // Solves the traveling salesman problem and caches solution.
    fun solve() {
        if (ranSolver) return
        val END_STATE = (1 shl N) - 1
        val memo = Array(N) { arrayOfNulls<Double>(1 shl N) }

        // Add all outgoing edges from the starting node to memo table.
        for (end in 0 until N) {
            if (end == start) continue
            memo[end][1 shl start or (1 shl end)] = distance[start][end]
        }
        for (r in 3..N) {
            for (subset in combinations(r, N)) {
                if (notIn(start, subset)) continue
                for (next in 0 until N) {
                    if (next == start || notIn(next, subset)) continue
                    val subsetWithoutNext = subset xor (1 shl next)
                    var minDist = Double.POSITIVE_INFINITY
                    for (end in 0 until N) {
                        if (end == start || end == next || notIn(end, subset)) continue
                        val newDistance = memo[end][subsetWithoutNext]!! + distance[end][next]
                        if (newDistance < minDist) {
                            minDist = newDistance
                        }
                    }
                    memo[next][subset] = minDist
                }
            }
        }

        // Connect tour back to starting node and minimize cost.
        for (i in 0 until N) {
            if (i == start) continue
            val tourCost = memo[i][END_STATE]!! + distance[i][start]
            if (tourCost < minTourCost) {
                minTourCost = tourCost
            }
        }
        var lastIndex = start
        var state = END_STATE
        tour.add(start)

        // Reconstruct TSP path from memo table.
        for (i in 1 until N) {
            var index = -1
            for (j in 0 until N) {
                if (j == start || notIn(j, state)) continue
                if (index == -1) index = j
                val prevDist = memo[index][state]!! + distance[index][lastIndex]
                val newDist = memo[j][state]!! + distance[j][lastIndex]
                if (newDist < prevDist) {
                    index = j
                }
            }
            tour.add(index)
            state = state xor (1 shl index)
            lastIndex = index
        }
        tour.add(start)
        Collections.reverse(tour)
        ranSolver = true
    }

    companion object {
        private fun notIn(elem: Int, subset: Int): Boolean {
            return 1 shl elem and subset == 0
        }

        // This method generates all bit sets of size n where r bits
        // are set to one. The result is returned as a list of integer masks.
        fun combinations(r: Int, n: Int): List<Int> {
            val subsets: MutableList<Int> = ArrayList()
            combinations(0, 0, r, n, subsets)
            return subsets
        }

        // To find all the combinations of size r we need to recurse until we have
        // selected r elements (aka r = 0), otherwise if r != 0 then we still need to select
        // an element which is found after the position of our last selected element
        private fun combinations(set: Int, at: Int, r: Int, n: Int, subsets: MutableList<Int>) {

            // Return early if there are more elements left to select than what is available.
            var set = set
            val elementsLeftToPick = n - at
            if (elementsLeftToPick < r) return

            // We selected 'r' elements so we found a valid subset!
            if (r == 0) {
                subsets.add(set)
            } else {
                for (i in at until n) {
                    // Try including this element
                    set = set or (1 shl i)
                    combinations(set, i + 1, r - 1, n, subsets)

                    // Backtrack and try the instance where we did not include this element
                    set = set and (1 shl i).inv()
                }
            }
        }
    }

    init {
        check(N > 2) { "N <= 2 not yet supported." }
        check(N == distance[0].size) { "Matrix must be square (n x n)" }
        require(!(start < 0 || start >= N)) { "Invalid start node." }
        this.start = start
        this.distance = distance
    }
}