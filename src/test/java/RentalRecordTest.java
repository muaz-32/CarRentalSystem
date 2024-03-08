import code.RentalRecord;
import code.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RentalRecordTest {
    private RentalRecord rentalRecord;
    private DateTime rentDate;
    private DateTime estimatedReturnDate;

    @BeforeEach
    public void setUp() {
        rentDate = new DateTime(1, 1, 2022);
        estimatedReturnDate = new DateTime(6, 1, 2022);
        rentalRecord = new RentalRecord("R_1", rentDate, estimatedReturnDate);
    }

    @Test
    public void constructor_shouldCreateNonNullObject() {
        RentalRecord rentalRecord = new RentalRecord("R_1", rentDate, estimatedReturnDate);
        assertNotNull(rentalRecord);
    }

    

    @Test
    public void getEstimatedReturnDate_shouldReturnCorrectDate() {
        assertEquals(estimatedReturnDate, rentalRecord.getEstimatedReturnDate());
    }

    @Test
    public void getRentDate_shouldReturnCorrectDate() {
        assertEquals(rentDate, rentalRecord.getRentDate());
    }

    @Test
    public void toString_shouldReturnCorrectStringWhenActualReturnDateIsNull() {
        String expected = "R_1:" + rentDate.toString() + ":" + estimatedReturnDate.toString() + ":none:none:none";
        assertEquals(expected, rentalRecord.toString());
    }

    @Test
    public void toString_shouldReturnCorrectStringWhenActualReturnDateIsNotNull() {
        DateTime actualReturnDate = new DateTime(7, 1, 2022);
        double rentalFee = 100.0;
        double lateFee = 20.0;
        rentalRecord.setData(actualReturnDate, rentalFee, lateFee);
        String expected = "R_1:" + rentDate.toString() + ":" + estimatedReturnDate.toString() + ":" + actualReturnDate.toString() + ":" + rentalFee + ":" + lateFee;
        assertEquals(expected, rentalRecord.toString());
    }

    @Test
    public void getDetails_shouldReturnCorrectDetailsWhenActualReturnDateIsNull() {
        String expected = "Record ID:             R_1\nRent Date:             " + rentDate.toString() + "\nEstimated Return Date: " + estimatedReturnDate.toString();
        assertEquals(expected, rentalRecord.getDetails());
    }

    @Test
    public void getDetails_shouldReturnCorrectDetailsWhenActualReturnDateIsNotNull() {
        DateTime actualReturnDate = new DateTime(7, 1, 2022);
        double rentalFee = 100.0;
        double lateFee = 20.0;
        rentalRecord.setData(actualReturnDate, rentalFee, lateFee);
        String expected = "Record ID:             R_1\nRent Date:             " + rentDate.toString() + "\nEstimated Return Date: " + estimatedReturnDate.toString() + "\nActual Return Date:    " + actualReturnDate.toString() + "\nRental Fee:            " + String.format("%.2f", rentalFee) + "\nLate Fee:              " + String.format("%.2f", lateFee);
        assertEquals(expected, rentalRecord.getDetails());
    }
}