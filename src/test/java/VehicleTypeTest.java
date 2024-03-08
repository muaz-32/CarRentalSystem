import code.DateTime;
import code.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleTypeTest {

    @Test
    public void carConstructor_shouldSetCarSeats() {
        VehicleType vehicleType = new VehicleType(4);
        assertEquals(4, vehicleType.getCarSeats());
    }

    @Test
    public void vanConstructor_shouldSetVanSeatsAndLastMaintenance() {
        DateTime lastMaintenance = new DateTime();
        VehicleType vehicleType = new VehicleType(15, lastMaintenance);
        assertEquals(15, vehicleType.getSeats("van"));
        assertEquals(lastMaintenance, vehicleType.getLastMaintenance());
    }

    @Test
    public void carConstructor_shouldNotSetVanSeats() {
        VehicleType vehicleType = new VehicleType(4);
        assertEquals(15, vehicleType.getSeats("van")); // default van seats
    }

    @Test
    public void vanConstructor_shouldNotSetCarSeats() {
        DateTime lastMaintenance = new DateTime();
        VehicleType vehicleType = new VehicleType(15, lastMaintenance);
        assertEquals(0, vehicleType.getCarSeats()); // default car seats
    }

    @Test
    public void getSeats_shouldReturnCarSeatsForCarType() {
        VehicleType vehicleType = new VehicleType(4);
        assertEquals(4, vehicleType.getSeats("car"));
    }

    @Test
    public void getSeats_shouldReturnVanSeatsForVanType() {
        VehicleType vehicleType = new VehicleType(15, new DateTime());
        assertEquals(15, vehicleType.getSeats("van"));
    }

    @Test
    public void getCarSeats_shouldReturnCarSeats() {
        VehicleType vehicleType = new VehicleType(4);
        assertEquals(4, vehicleType.getCarSeats());
    }

    @Test
    public void setCarSeats_shouldSetCarSeats() {
        VehicleType vehicleType = new VehicleType(4);
        vehicleType.setCarSeats(5);
        assertEquals(5, vehicleType.getCarSeats());
    }

    @Test
    public void getLastMaintenance_shouldReturnLastMaintenanceDate() {
        DateTime lastMaintenance = new DateTime();
        VehicleType vehicleType = new VehicleType(15, lastMaintenance);
        assertEquals(lastMaintenance, vehicleType.getLastMaintenance());
    }

    @Test
    public void setLastMaintenance_shouldSetLastMaintenanceDate() {
        DateTime lastMaintenance = new DateTime();
        VehicleType vehicleType = new VehicleType(15, new DateTime());
        vehicleType.setLastMaintenance(lastMaintenance);
        assertEquals(lastMaintenance, vehicleType.getLastMaintenance());
    }

    @Test
    public void canBeRentedForMinimumDays_shouldReturnTwoForWeekdayAndCar() {
        VehicleType vehicleType = new VehicleType(4);
        DateTime date = new DateTime(1, 1, 2023); // This is a Monday
        assertEquals(2, vehicleType.canBeRentedForMinimumDays(date, "car"));
    }

    @Test
    public void canBeRentedForMinimumDays_shouldReturnThreeForWeekendAndCar() {
        VehicleType vehicleType = new VehicleType(4);
        DateTime date = new DateTime(6, 1, 2023); // This is a Saturday
        assertEquals(3, vehicleType.canBeRentedForMinimumDays(date, "car"));
    }

    @Test
    public void canBeRentedForMinimumDays_shouldReturnOneForVan() {
        VehicleType vehicleType = new VehicleType(15, new DateTime());
        DateTime date = new DateTime(1, 1, 2023); // This is a Monday
        assertEquals(1, vehicleType.canBeRentedForMinimumDays(date, "van"));
    }

    @Test
    public void isUnderMaintenance_shouldReturnFalseForCar() {
        DateTime lastMaintenance = new DateTime(1, 1, 2023);
        VehicleType vehicleType = new VehicleType(4, lastMaintenance);
        DateTime rentDate = new DateTime(1, 1, 2023);
        assertFalse(vehicleType.IsUnderMaintenance(rentDate, "car", 1));
    }

    @Test
    public void isUnderMaintenance_shouldReturnFalseForVanNotUnderMaintenance() {
        DateTime lastMaintenance = new DateTime(1, 1, 2023);
        VehicleType vehicleType = new VehicleType(15, lastMaintenance);
        DateTime rentDate = new DateTime(3, 1, 2023);
        assertFalse(vehicleType.IsUnderMaintenance(rentDate, "van", 1));
    }

    @Test
    public void isUnderMaintenance_shouldReturnTrueForVanUnderMaintenance() {
        DateTime lastMaintenance = new DateTime(1, 1, 2023);
        VehicleType vehicleType = new VehicleType(15, lastMaintenance);
        DateTime rentDate = new DateTime(1, 1, 2023);
        assertTrue(vehicleType.IsUnderMaintenance(rentDate, "van", 13));
    }
}