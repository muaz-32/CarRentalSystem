import code.RentSystem;
import code.Car;
import code.Van;
import code.DateTime;
import code.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class RentSystemTest {
    private RentSystem rentSystem;
    private Car car;
    private Van van;

    @BeforeEach
    public void setUp() {
        rentSystem = new RentSystem();
        car = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        van = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));
    }

    @Test
    public void add_shouldAddVehicleToSystem() {
        String input = "car\n2022\nToyota\nCamry\n1\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));
        assertNotNull(rentSystem.cars[0]);

        input = "van\n2022\nToyota\nCamry\n1\n15\n01/01/2022\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));
        assertNotNull(rentSystem.vans[0]);
    }

    @Test
    public void add_shouldNotAddVehicleWithInvalidType() {
        String input = "bike\n2022\nToyota\nCamry\n1\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));
    }

    @Test
    public void add_shouldNotAddVehicleWithInvalidYear() {
        String input = "car\n3000\nToyota\nCamry\n1\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));
    }

    @Test
    public void add_shouldNotAddCarWithInvalidSeats() {
        String input = "car\n2022\nToyota\nCamry\n1\n3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));
    }

    @Test
    public void add_shouldNotAddVehicleWithExistingId() {
        String input = "car\n2022\nToyota\nCamry\n1\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));

        int initialSize = rentSystem.cars.length;

        input = "car\n2022\nToyota\nCamry\n1\n4\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.add(new Scanner(System.in));

        int finalSize = rentSystem.cars.length;

        assertEquals(initialSize, finalSize);
    }

    @Test
    public void add_shouldNotAddVanWithInvalidMaintenanceDate() {
        String input = "van\n2022\nToyota\nCamry\n1\n30/2/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertThrows(Exception.class, () -> rentSystem.add(new Scanner(System.in)));
    }

    @Test
    public void rent_shouldRentCarWhenAvailable() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));

        String input = "C_1\nCUST_1\n01/01/2022\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.rent(new Scanner(System.in));

        assertEquals(1, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void rent_shouldNotRentCarWhenUnavailable() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));

        String input = "C_1\nCUST_1\n01/01/2022\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.rent(new Scanner(System.in));

        assertEquals(1, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void rent_shouldRentVanWhenAvailable() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));

        String input = "V_1\nCUST_1\n01/01/2022\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.rent(new Scanner(System.in));

        assertEquals(1, rentSystem.vans[0].vehicleStatus);
    }

    @Test
    public void rent_shouldNotRentVanWhenUnavailable() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(15, new DateTime(1, 1, 2022)));

        String input = "V_1\nCUST_1\n01/01/2022\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.rent(new Scanner(System.in));

        assertEquals(1, rentSystem.vans[0].vehicleStatus);
    }

    @Test
    public void rent_shouldNotRentWhenVehicleIdIsInvalid() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));

        String input = "C_2\nCUST_1\n01/01/2022\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.rent(new Scanner(System.in));

        assertEquals(0, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void returnVehicle_shouldReturnCarWhenRented() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));

        // Rent the car first
        String rentInput = "C_1\nCUST_1\n01/01/2022\n5\n";
        InputStream rentIn = new ByteArrayInputStream(rentInput.getBytes());
        System.setIn(rentIn);
        rentSystem.rent(new Scanner(System.in));

        // Now return the rented car
        String returnInput = "C_1\n01/02/2022\n";
        InputStream returnIn = new ByteArrayInputStream(returnInput.getBytes());
        System.setIn(returnIn);
        rentSystem.returnCar(new Scanner(System.in));

        assertEquals(0, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void returnVehicle_shouldNotReturnCarWhenNotRented() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));

        String input = "C_1\n1/2/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertThrows(Exception.class, () -> rentSystem.returnCar(new Scanner(System.in)));
    }

    @Test
    public void returnVehicle_shouldReturnVanWhenRented() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));

        // Rent the van first
        String rentInput = "V_1\nCUST_1\n01/01/2022\n5\n";
        InputStream rentIn = new ByteArrayInputStream(rentInput.getBytes());
        System.setIn(rentIn);
        rentSystem.rent(new Scanner(System.in));

        // Now return the rented van
        String returnInput = "V_1\n01/02/2022\n";
        InputStream returnIn = new ByteArrayInputStream(returnInput.getBytes());
        System.setIn(returnIn);
        rentSystem.returnCar(new Scanner(System.in));

        assertEquals(0, rentSystem.vans[0].vehicleStatus);
    }

    @Test
    public void returnVehicle_shouldNotReturnVanWhenNotRented() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(15, new DateTime(1, 1, 2022)));

        String input = "V_1\n1/2/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        assertThrows(Exception.class, () -> rentSystem.returnCar(new Scanner(System.in)));
    }

    @Test
    public void returnVehicle_shouldNotReturnWhenVehicleIdIsInvalid() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));

        String input = "C_2\n1/2/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.returnCar(new Scanner(System.in));
        
        assertEquals(1, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void vehicleMaintenance_shouldSetCarToMaintenanceWhenAvailable() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));
        
        String input = "C_1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.vehicleMaintenance(new Scanner(System.in));

        assertEquals(2, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void vehicleMaintenance_shouldNotSetCarToMaintenanceWhenRented() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));
        
        String input = "C_1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.vehicleMaintenance(new Scanner(System.in));

        assertEquals(1, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void vehicleMaintenance_shouldSetVanToMaintenanceWhenAvailable() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));

        String input = "V_1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.vehicleMaintenance(new Scanner(System.in));

        assertEquals(2, rentSystem.vans[0].vehicleStatus);
    }

    @Test
    public void vehicleMaintenance_shouldNotSetVanToMaintenanceWhenRented() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(15, new DateTime(1, 1, 2022)));
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 1, new VehicleType(5));

        String input = "V_1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.vehicleMaintenance(new Scanner(System.in));

        assertEquals(1, rentSystem.vans[0].vehicleStatus);
    }

    @Test
    public void vehicleMaintenance_shouldNotSetVehicleToMaintenanceWhenIdIsInvalid() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(15, new DateTime(1, 1, 2022)));

        String input = "C_2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.vehicleMaintenance(new Scanner(System.in));

        assertEquals(0, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void completeMaintenance_shouldCompleteCarMaintenanceWhenInMaintenance() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 2, new VehicleType(5));
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(15, new DateTime(1, 1, 2022)));

        String input = "C_1\n01/02/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.completeMaintenance(new Scanner(System.in));

        assertEquals(0, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void completeMaintenance_shouldNotCompleteCarMaintenanceWhenNotInMaintenance() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 1, new VehicleType(15, new DateTime(1, 1, 2022)));

        String input = "C_1\n01/02/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.completeMaintenance(new Scanner(System.in));

        assertEquals(0, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void completeMaintenance_shouldCompleteVanMaintenanceWhenInMaintenance() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 2, new VehicleType(15, new DateTime(1, 1, 2022)));
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));

        String input = "V_1\n01/02/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.completeMaintenance(new Scanner(System.in));

        assertEquals(0, rentSystem.vans[0].vehicleStatus);
    }

    @Test
    public void completeMaintenance_shouldNotCompleteVanMaintenanceWhenNotInMaintenance() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));

        String input = "V_1\n01/02/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.completeMaintenance(new Scanner(System.in));

        assertEquals(0, rentSystem.vans[0].vehicleStatus);
    }

    @Test
    public void completeMaintenance_shouldNotCompleteMaintenanceWhenVehicleIdIsInvalid() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 2, new VehicleType(5));
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));

        String input = "C_2\n01/02/2022\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        rentSystem.completeMaintenance(new Scanner(System.in));

        assertEquals(2, rentSystem.cars[0].vehicleStatus);
    }

    @Test
    public void getDetails_shouldDisplayAllVehiclesWhenVehiclesExist() {
        RentSystem rentSystem = new RentSystem();
        rentSystem.cars[0] = new Car("C_1", 2022, "Toyota", "Camry", 0, new VehicleType(5));
        rentSystem.vans[0] = new Van("V_1", 2022, "Toyota", "Camry", 0, new VehicleType(15, new DateTime(1, 1, 2022)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        rentSystem.getDetails();

        String expectedOutput = "C_1\nV_1\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void getDetails_shouldDisplayNoVehiclesWhenNoVehiclesExist() {
        RentSystem rentSystem = new RentSystem();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        rentSystem.getDetails();

        String expectedOutput = "No vehicles exist.\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}