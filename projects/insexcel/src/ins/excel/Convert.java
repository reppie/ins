package ins.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Convert {

	public static final String PATH = "data/";
	public static final String EXTENSION = ".xlsx";
	
	public static final int START_AT_ROW = 2;
	
	public final String FILENAME;
	public final int ROWCOUNT;
	public final int AXESCOUNT;
	public final int[] INTERVAL;
	
	public Convert(String fileName, int rowCount, int axesCount, int[] interval) {
		FILENAME = PATH + fileName + EXTENSION;
		ROWCOUNT = rowCount;
		AXESCOUNT = axesCount;
		INTERVAL = interval;
		
		System.out.println("FILENAME: " + FILENAME);
		System.out.println("ROWCOUNT: " + ROWCOUNT);
		System.out.println("AXESCOUNT: " + AXESCOUNT);
		System.out.print("INTERVAL: ");
		for (int i = 0; i < INTERVAL.length; i++) {
			System.out.print(INTERVAL[i]);
			if (i < INTERVAL.length - 1) {
				System.out.print(",");
			} else {
				System.out.print("\n");
			}
		}
		System.out.println();
		
		FileInputStream in = null;
		FileOutputStream out = null;
		try {						
			in = new FileInputStream(FILENAME);
			
			Workbook wb = WorkbookFactory.create(in);
			
			String sheetName = "";
			for (int i = 0; i < INTERVAL.length; i++) {
				sheetName = "Interval = " + INTERVAL[i];
				
				// remove sheet if one with identical name exists
				if (wb.getSheet(sheetName) != null) {
					wb.removeSheetAt(wb.getSheetIndex(sheetName));
				}
				
				System.out.println("(Interval = " + INTERVAL[i] + ") Building data..");
				
				// create new sheet
				Sheet sheet = wb.createSheet(sheetName);
				// create rows in sheet
				for (int j = 0; j <= ROWCOUNT; j++) {
					sheet.createRow(j);
				}
				// copy columns in sheet
				copyColumn(wb.getSheetAt(0), sheet, 0, INTERVAL[i]);
				copyColumn(wb.getSheetAt(0), sheet, 1, INTERVAL[i]);
				
				for (int j = 0; j < AXESCOUNT; j++) {
					copyColumn(wb.getSheetAt(0), sheet, j + 2, INTERVAL[i]);
				}
			}
			
			System.out.println("\nWriting to file..");
			
			// write workbook to file
			out = new FileOutputStream(FILENAME);
			wb.write(out);
			
			System.out.println("Done");
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void copyColumn(Sheet fromSheet, Sheet toSheet, int column, int interval) {
		Row fromRow, toRow = null;
		Cell fromCell, toCell = null;
		
		// header
		fromRow = fromSheet.getRow(START_AT_ROW - 1);
		fromCell = fromRow.getCell(column);
		
		String header = fromCell.getStringCellValue();
		
		toRow = toSheet.getRow(START_AT_ROW - 1);
		toCell = toRow.createCell(column);
		toCell.setCellValue(header);
		
		// data
		double value = 0;
		int rowIndex = START_AT_ROW;
		for (int i = START_AT_ROW; i <= (ROWCOUNT + START_AT_ROW); i += interval) {
			fromRow = fromSheet.getRow(i);
			fromCell = fromRow.getCell(column);
			
			value = fromCell.getNumericCellValue();
			
			toRow = toSheet.getRow(rowIndex);
			toCell = toRow.createCell(column);
			toCell.setCellValue(value);
			
			rowIndex++;
		}
	}
	
	public static void main(String[] args) {
		String[] strInterval = args[3].split(",");
		int[] interval = new int[strInterval.length];
		for (int j = 0; j < interval.length; j++) {
			interval[j] = Integer.parseInt(strInterval[j]);
		}
		
		new Convert(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), interval);
	}

}
