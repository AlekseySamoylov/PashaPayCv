package com.alekseysamoylov

import java.math.BigDecimal


class ScoringService(val bureauService: BureauService, val minScore: BigDecimal, val scoringFormula: ScoringFormula = object : ScoringFormula {}) {
    fun score(loanRequest: LoanRequest): ScoringResult {
        return if (loanRequest.employmentStatus == EmploymentStatus.UNEMPLOYED) {
            ScoringResult.REJECTED
        } else {
            requestToBureau(loanRequest)
        }
    }

    private fun requestToBureau(loanRequest: LoanRequest): ScoringResult {
        val creditHistoryCoefficient = bureauService.getStatus(loanRequest.userData)
        val scoreResult = scoringFormula.getScore(loanRequest.employmentStatus.coefficient, loanRequest.martialStatus.coefficient, creditHistoryCoefficient)
        return if (scoreResult > minScore) {
            ScoringResult.APPROVED
        } else {
            ScoringResult.REJECTED
        }
    }
}


enum class ScoringResult {
    APPROVED, REJECTED
}
