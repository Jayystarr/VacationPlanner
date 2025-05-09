package com.example.d308vacationplanner;

import static org.junit.Assert.assertEquals;

import com.example.d308vacationplanner.entities.Vacation;

import org.junit.Test;

public class VacationRepositoryTest {

    //TESTING

    @Test
    public void testVacationObjectCreation() {
        // Arrange
        String vacationName = "Test Vacation";
        String hotelName = "Test Hotel";
        String startDate = "2025-06-01";
        String endDate = "2025-06-10";

        // Act
        Vacation vacation = new Vacation(vacationName, hotelName, startDate, endDate);

        // Assert
        assertEquals(vacationName, vacation.getVacationName());
        assertEquals(hotelName, vacation.getVacationHotel());
        assertEquals(startDate, vacation.getStartDate());
        assertEquals(endDate, vacation.getEndDate());
    }
}
