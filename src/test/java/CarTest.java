import code.Car;
import code.DateTime;
import code.RentalRecord;
import code.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CarTest {
    private Car car;
    private DateTime startDate;
    private DateTime endDate;

    @BeforeEach
    public void setUp() {
        car = new Car("C_1", 2019, "Toyota", "Camry", 0, new VehicleType(5));
        startDate = new DateTime(1, 1, 2020);
        endDate = new DateTime(6, 1, 2020);
    }

    @Test
    public void getLateFee_shouldCalculateCorrectLateFee() {
        assertEquals(1.25 * 78 * DateTime.diffDays(endDate, startDate), car.getLateFee(endDate, startDate));
    }

    @Test
    public void returnVehicle_shouldReturnTrueWhenVehicleIsRented() {
        car.vehicleStatus = 1;
        DateTime returnDate = new DateTime(6, 1, 2020);
        DateTime estdate = new DateTime(8, 1, 2020);
        DateTime rentDate = new DateTime(1, 1, 2020);
        car.records[0] = new RentalRecord("C_1", rentDate, estdate);
        assertTrue(car.returnVehicle(returnDate));
    }

    @Test
    public void returnVehicle_shouldReturnFalseWhenVehicleIsNotRented() {
        car.vehicleStatus = 0;
        DateTime returnDate = new DateTime(6, 1, 2020);
        assertFalse(car.returnVehicle(returnDate));
    }

    @Test
    public void completeMaintenance_shouldReturnTrueWhenVehicleIsUnderMaintenance() {
        car.vehicleStatus = 2;
        assertTrue(car.completeMaintenance());
    }

    @Test
    public void completeMaintenance_shouldReturnFalseWhenVehicleIsNotUnderMaintenance() {
        car.vehicleStatus = 0;
        assertFalse(car.completeMaintenance());
    }

    @Test
    public void getDetails_shouldReturnCorrectDetailsForAvailableCar() {
        VehicleType vehicleType = new VehicleType(5);
        Car car = new Car("C_1", 2022, "Toyota", "Camry", 0, vehicleType);
        String expected = "Vehicle ID:\tC_1\n Year:\t 2022\n Make:\tToyota\n Model:\tCamry\n Number of Seats:\t5\n Status:\tAvailable\nRENTAL RECORD:\tempty";
        assertEquals(expected, car.getDetails());
    }
    
    @Test
    public void getDetails_shouldReturnCorrectDetailsForRentedCar() {
        VehicleType vehicleType = new VehicleType(5);
        Car car = new Car("C_1", 2022, "Toyota", "Camry", 1, vehicleType);
        String expected = "Vehicle ID:\tC_1\n Year:\t 2022\n Make:\tToyota\n Model:\tCamry\n Number of Seats:\t5\n Status:\tRented\nRENTAL RECORD:\tempty";
        assertEquals(expected, car.getDetails());
    }
    
    @Test
    public void getDetails_shouldReturnCorrectDetailsForCarUnderMaintenance() {
        VehicleType vehicleType = new VehicleType(5);
        Car car = new Car("C_1", 2022, "Toyota", "Camry", 2, vehicleType);
        String expected = "Vehicle ID:\tC_1\n Year:\t 2022\n Make:\tToyota\n Model:\tCamry\n Number of Seats:\t5\n Status:\tMaintenance\nRENTAL RECORD:\tempty";
        assertEquals(expected, car.getDetails());
    }
}