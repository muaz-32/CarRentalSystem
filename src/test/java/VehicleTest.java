import code.Vehicle;
import code.DateTime;
import code.VehicleType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VehicleTest {

    @Test
    public void getVehicleId_shouldReturnCorrectId() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        assertEquals("V_1", vehicle.getVehicleId());
    }

    @Test
    public void rent_shouldReturnFalseWhenVehicleIsNotAvailable() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));
        boolean result = vehicle.rent("C_1", new DateTime(1, 1, 2022), 7);
        assertEquals(false, result);
    }

    @Test
    public void rent_shouldReturnTrueWhenVehicleIsAvailable() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(5, new DateTime(1, 1, 2022)));
        boolean result = vehicle.rent("C_1", new DateTime(3, 1, 2022), 7);
        assertEquals(true, result);
    }

    @Test
    public void performMaintenance_shouldReturnFalseWhenVehicleIsRented() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));
        boolean result = vehicle.performMaintenance();
        assertEquals(false, result);
    }

    @Test
    public void performMaintenance_shouldReturnTrueWhenVehicleIsAvailable() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        boolean result = vehicle.performMaintenance();
        assertEquals(true, result);
    }

    @Test
    public void getDetails_shouldReturnCorrectDetails() {
        Vehicle vehicle = new Vehicle("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        String expected = "Vehicle ID:\tC_1\n Year:\t 2022\n Make:\tToyota\n Model:\tCamry\n Number of Seats:\t5\n Status:\tAvailable";
        assertEquals(expected, vehicle.getDetails());
    }

    @Test
    public void getLastElementIndex_shouldReturnCorrectIndex() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        int expected = -1; // As no records have been added yet
        assertEquals(expected, vehicle.getLastElementIndex());
    }

    @Test
    public void toString_shouldReturnCorrectFormatForCar() {
        Vehicle vehicle = new Vehicle("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        String expected = "C_1:2022:Toyota:Camry:5:Available";
        assertEquals(expected, vehicle.toString());
    }

    @Test
    public void toString_shouldReturnCorrectFormatForVan() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));
        String expected = "V_1:2022:Toyota:Camry:15:Available";
        assertEquals(expected, vehicle.toString());
    }

    @Test
    public void toString_shouldReturnCorrectFormatWhenVehicleIsRented() {
        Vehicle vehicle = new Vehicle("C_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));
        String expected = "C_1:2022:Toyota:Camry:5:Rented";
        assertEquals(expected, vehicle.toString());
    }

    @Test
    public void toString_shouldReturnCorrectFormatWhenVehicleIsUnderMaintenance() {
        Vehicle vehicle = new Vehicle("V_1", 2022, "Toyota", "Camry", 2, new VehicleType(15, new DateTime(1, 1, 2022)));
        String expected = "V_1:2022:Toyota:Camry:15:Maintenance";
        assertEquals(expected, vehicle.toString());
    }
}