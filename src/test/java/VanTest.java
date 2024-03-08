import code.RentalRecord;
import code.Van;
import code.DateTime;
import code.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VanTest {
    private Van van;
    private DateTime startDate;
    private DateTime endDate;

    @BeforeEach
    public void setUp() {
        van = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(5, new DateTime(1, 1, 2022)));
        startDate = new DateTime(1, 1, 2022);
        endDate = new DateTime(6, 1, 2022);
    }

    @Test
    public void getLateFee_shouldCalculateCorrectLateFee() {
        assertEquals(299 * DateTime.diffDays(endDate, startDate), van.getLateFee(endDate, startDate));
    }

    @Test
    public void returnVehicle_shouldReturnTrueWhenVehicleIsRented() {
        van.vehicleStatus = 1;
        DateTime returnDate = new DateTime(6, 1, 2022);
        DateTime estdate = new DateTime(8, 1, 2022);
        DateTime rentDate = new DateTime(1, 1, 2022);
        van.records[0] = new RentalRecord("V_1", rentDate, estdate);
        assertTrue(van.returnVehicle(returnDate));
    }

    @Test
    public void returnVehicle_shouldReturnFalseWhenVehicleIsNotRented() {
        van.vehicleStatus = 0;
        DateTime returnDate = new DateTime(6, 1, 2022);
        assertFalse(van.returnVehicle(returnDate));
    }

    @Test
    public void completeMaintenance_shouldReturnTrueWhenVehicleIsUnderMaintenance() {
        van.vehicleStatus = 2;
        DateTime completionDate = new DateTime(1, 1, 2022);
        assertTrue(van.completeMaintenance(completionDate));
    }

    @Test
    public void completeMaintenance_shouldReturnFalseWhenVehicleIsNotUnderMaintenance() {
        van.vehicleStatus = 0;
        DateTime completionDate = new DateTime(1, 1, 2022);
        assertFalse(van.completeMaintenance(completionDate));
    }

    @Test
    public void getDetails_shouldReturnCorrectDetailsForAvailableVan() {
        VehicleType vehicleType = new VehicleType(5, new DateTime(1, 1, 2022));
        Van van = new Van("V_1", 2022, "Toyota", "Camry", 0, vehicleType);
        String expected = "Vehicle ID:\tV_1\n Year:\t 2022\n Make:\tToyota\n Model:\tCamry\n Number of Seats:\t5\n Status:\tAvailable\nLast maintenance date:\t"+(van.vehicleType.getLastMaintenance()).toString()+"\nRENTAL RECORD:\tempty";
        assertEquals(expected, van.getDetails());
    }
    
    @Test
    public void getDetails_shouldReturnCorrectDetailsForRentedVan() {
        VehicleType vehicleType = new VehicleType(5, new DateTime(1, 1, 2022));
        Van van = new Van("V_1", 2022, "Toyota", "Camry", 1, vehicleType);
        String expected = "Vehicle ID:\tV_1\n Year:\t 2022\n Make:\tToyota\n Model:\tCamry\n Number of Seats:\t5\n Status:\tRented\nLast maintenance date:\t"+(van.vehicleType.getLastMaintenance()).toString()+"\nRENTAL RECORD:\tempty";
        assertEquals(expected, van.getDetails());
    }
    
    @Test
    public void getDetails_shouldReturnCorrectDetailsForVanUnderMaintenance() {
        VehicleType vehicleType = new VehicleType(5, new DateTime(1, 1, 2022));
        Van van = new Van("V_1", 2022, "Toyota", "Camry", 2, vehicleType);
        String expected = "Vehicle ID:\tV_1\n Year:\t 2022\n Make:\tToyota\n Model:\tCamry\n Number of Seats:\t5\n Status:\tMaintenance\nLast maintenance date:\t"+(van.vehicleType.getLastMaintenance()).toString()+"\nRENTAL RECORD:\tempty";
        assertEquals(expected, van.getDetails());
    }
}