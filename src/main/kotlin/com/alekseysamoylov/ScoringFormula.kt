package com.alekseysamoylov

import java.math.BigDecimal


interface ScoringFormula {
    fun getScore(employmentCoefficient: Double, martialStatusCoefficient: Double, creditHistoryCoefficient: Double): BigDecimal {
        return BigDecimal.valueOf((employmentCoefficient + martialStatusCoefficient + creditHistoryCoefficient) / 3.0)
    }
}
