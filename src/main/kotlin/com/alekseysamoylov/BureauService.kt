package com.alekseysamoylov


interface BureauService {
    /**
     * return:
     * 0 - has active loan
     * 0.5 - don't have active loan, but has overdue payments
     * 1 - don't have active loan and don't have overdue payments
     */
    fun getStatus(userData: String): Double
}

