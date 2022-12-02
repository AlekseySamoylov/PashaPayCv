package com.alekseysamoylov


data class LoanRequest(val userData: String, val employmentStatus: EmploymentStatus, val martialStatus: MartialStatus)

enum class MartialStatus(val coefficient: Double) {
    SINGLE(0.0), MARRIED(1.0)
}

enum class EmploymentStatus(val coefficient: Double) {
    UNEMPLOYED(0.0), EMPLOYED(1.0), BUSINESS(0.5)
}
