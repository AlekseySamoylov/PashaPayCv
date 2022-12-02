package com.alekseysamoylov

import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.lenient
import org.mockito.kotlin.*
import java.math.BigDecimal
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test


class ScoringServiceTest {
    private lateinit var scoringService: ScoringService
    private lateinit var userData: String
    private lateinit var bureauServiceMock: BureauService
    private lateinit var scoringFormulaMock: ScoringFormula
    private final val minimumCoefficientToApprove = 0.8

    @BeforeTest
    fun setup() {
        bureauServiceMock = mock()
        scoringFormulaMock = mock()
        scoringService = ScoringService(bureauServiceMock, BigDecimal.valueOf(minimumCoefficientToApprove), scoringFormulaMock)
        userData = UUID.randomUUID().toString()
    }

    @Test
    fun `Должны отказать в займе берзаботному, без запроса в кредитное бюро`() {
        // Given
        val employmentStatus = EmploymentStatus.UNEMPLOYED
        val martialStatus = MartialStatus.MARRIED
        val loanRequest = LoanRequest(userData, employmentStatus, martialStatus)

        // When
        val result = scoringService.score(loanRequest)

        // Then
        assertThat(result).isEqualTo(ScoringResult.REJECTED)
        verify(bureauServiceMock, times(0)).getStatus(userData)
    }

    @Test
    fun `Должны одобрить займ для трудоустроенного, женатого клиента, без истории займов`() {
        // Given
        val employmentStatus = EmploymentStatus.EMPLOYED
        val martialStatus = MartialStatus.MARRIED
        val loanRequest = LoanRequest(userData, employmentStatus, martialStatus)
        lenient().whenever(bureauServiceMock.getStatus(userData)).doReturn(1.0)
        lenient().whenever(scoringFormulaMock.getScore(1.0, 1.0, 1.0)).thenReturn(BigDecimal.valueOf(1.0))

        // When
        val result = scoringService.score(loanRequest)

        // Then
        assertThat(result).isEqualTo(ScoringResult.APPROVED)
        verify(bureauServiceMock, times(1)).getStatus(userData)
        verify(scoringFormulaMock, times(1)).getScore(1.0, 1.0, 1.0)
    }

    // TODO (Aleksey Samoylov) write other tests...

}
