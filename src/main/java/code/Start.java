package code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class Start {
	public static void main(String[] args)
	{
		RentSystem rentSystem =new RentSystem();
		rentSystem.run();
	}

}



class WriteToExcelJExcel {
	public static void main(String[] args) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File("testcases.xls"));
			WritableSheet sheet = workbook.createSheet("Test Cases", 0);

			String[][] data = {
					{"Test Case ID", "Test Case Summary", "Test Procedure", "Expected Result", "Actual Result"},
					{"TC64", "Vehicle is added to the system", "Add a car and a van to the system", "Car and van are added to the system", "To be filled after test execution"},
					{"TC65", "Vehicle is not added with invalid type", "Try to add a bike to the system", "Exception is thrown", "To be filled after test execution"},
					{"TC66", "Vehicle is not added with invalid year", "Try to add a car with year 3000 to the system", "Exception is thrown", "To be filled after test execution"},
					{"TC67", "Car is not added with invalid seats", "Try to add a car with 3 seats to the system", "Exception is thrown", "To be filled after test execution"},
					{"TC68", "Vehicle is not added with existing ID", "Try to add a car with an ID that already exists in the system", "Car is not added to the system", "To be filled after test execution"},
					{"TC69", "Van is not added with invalid maintenance date", "Try to add a van with maintenance date 30/2/2022 to the system", "Exception is thrown", "To be filled after test execution"},
					{"TC70", "Car is rented when available", "Rent a car that are available in the system", "Car is rented", "To be filled after test execution"},
					{"TC71", "Van is rented when available", "Rent a van that are available in the system", "Van is rented", "To be filled after test execution"},
					{"TC72", "Car is not rented when unavailable", "Try to rent a car that is unavailable in the system", "Car is not rented", "To be filled after test execution"},
					{"TC73", "Van is not rented when unavailable", "Try to rent a van that is unavailable in the system", "Van is not rented", "To be filled after test execution"},
					{"TC74", "Vehicle is not rented when ID is invalid", "Try to rent a vehicle with an ID that does not exist in the system", "Vehicle is not rented", "To be filled after test execution"},
					{"TC75", "Car is returned when rented", "Return a rented car", "Car is returned", "To be filled after test execution"},
					{"TC76", "Van is returned when rented", "Return a rented van", "Van is returned", "To be filled after test execution"},
					{"TC77", "Car is not returned when not rented", "Try to return a car that is not rented", "Exception is thrown", "To be filled after test execution"},
					{"TC78", "Van is not returned when not rented", "Try to return a van that is not rented", "Exception is thrown", "To be filled after test execution"},
					{"TC79", "Vehicle is not returned when ID is invalid", "Try to return a vehicle with an ID that does not exist in the system", "Vehicle is not returned", "To be filled after test execution"},
					{"TC80", "Car is set to maintenance when available", "Set a car that is available to maintenance", "Car is set to maintenance", "To be filled after test execution"},
					{"TC81", "Van is set to maintenance when available", "Set a van that is available to maintenance", "Van is set to maintenance", "To be filled after test execution"},
					{"TC82", "Car is not set to maintenance when unavailable", "Try to set a car that is unavailable to maintenance", "Car is not set to maintenance", "To be filled after test execution"},
					{"TC83", "Van is not set to maintenance when unavailable", "Try to set a van that is unavailable to maintenance", "Van is not set to maintenance", "To be filled after test execution"},
					{"TC84", "Vehicle is not set to maintenance when ID is invalid", "Try to set a vehicle with an ID that does not exist in the system to maintenance", "Vehicle is not set to maintenance", "To be filled after test execution"},
					{"TC85", "Maintenance is completed when car in maintenance", "Complete maintenance for a car that is in maintenance", "Maintenance for car is completed", "To be filled after test execution"},
					{"TC86", "Maintenance is completed when van in maintenance", "Complete maintenance for a van that is in maintenance", "Maintenance for van is completed", "To be filled after test execution"},
					{"TC87", "Maintenance is not completed when car not in maintenance", "Try to complete maintenance for a car that is not in maintenance", "Maintenance for car is not completed", "To be filled after test execution"},
					{"TC88", "Maintenance is not completed when van not in maintenance", "Try to complete maintenance for a van that is not in maintenance", "Maintenance for van is not completed", "To be filled after test execution"},
					{"TC89", "Maintenance is not completed when ID is invalid", "Try to complete maintenance for a vehicle with an ID that does not exist in the system", "Maintenance is not completed", "To be filled after test execution"},
					{"TC90", "All vehicles are displayed when vehicles exist", "Display all vehicles when there are vehicles in the system", "All vehicles are displayed", "To be filled after test execution"},
					{"TC91", "No vehicles are displayed when no vehicles exist", "Display all vehicles when there are no vehicles in the system", "No vehicles are displayed", "To be filled after test execution"},
					
            };

			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					Label label = new Label(j, i, data[i][j]);
					sheet.addCell(label);
				}
			}

			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}