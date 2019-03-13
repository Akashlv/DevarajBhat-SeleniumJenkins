package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class ExcelUtility {
	
	
	static Logger logger = Logger.getLogger("ExcelUtility");

	static int lastRowNum;

//	static String testDataFile = System.getProperty("user.dir").concat("\\src\\test\\resources\\TestdataandResults_Sprint8.xlsx");
	// static File testDataFile = new
	// File(ClassLoader.getSystemResource("Telco_TestData.xlsx").getFile());
	static FileInputStream fis;
	static XSSFWorkbook wb;
	static XSSFSheet ws;
	static XSSFCellStyle cellStyle;
	static Font font;
	static XSSFCell Cell, Cell2;
	static XSSFRow row;
	static SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmms");

	// static String name;

	public static int excelInitialize(String testDataFileName, String sheetName) throws IOException {
		// testDataFile = new
		// File(ClassLoader.getSystemResource("Telco_TestData.xlsx").getFile());
		String testDataFile = System.getProperty("user.dir").concat("\\src\\test\\resources\\"+testDataFileName);
		
		fis = new FileInputStream(testDataFile);
		wb = new XSSFWorkbook(fis);
		ws = wb.getSheet(sheetName);
		lastRowNum = ws.getLastRowNum();
		System.out.println("Sheet Name excelInitialize :"+sheetName);

		return lastRowNum;
	}

	public static String getStringCellData(int i, int j) throws IOException {

		String stringCellValue = null;

		Cell = ws.getRow(i).getCell(j);

		stringCellValue = Cell.getStringCellValue();

		fis.close();

		return stringCellValue;

	}

	public static int getNumericCellData(int i, int j) throws IOException {

		int numericCellValue;

		Cell = ws.getRow(i).getCell(j);

		numericCellValue = (int) Cell.getNumericCellValue();

		fis.close();

		return numericCellValue;

	}

	public static Date getDateCellDatae(int i, int j) throws IOException {

		Date dateCellValue;

		Cell = ws.getRow(i).getCell(j);

		dateCellValue = Cell.getDateCellValue();

		fis.close();

		return dateCellValue;

	}
	
	public static int getRowContains(String sTestCaseName, int colNum) throws Exception{
		int i;
		try {
//			System.out.println("sTestCasename :"+sTestCaseName+" "+sTestCaseName.length());
			
//			int rowCount = ExcelUtils.getRowUsed();
			
//			String[] tempTCName = sTestCaseName.split(".");
//			for (String string : tempTCName) {
//				System.out.println(string);
//			}
//			String[] temp = tempTCName[1].split("@");
			
			for ( i=1 ; i<=ws.getLastRowNum(); i++){
				System.out.println("Cell data :"+getStringCellData(i,0));
				if  (sTestCaseName.contains(getStringCellData(i,0))){
					break;
				}
			}
			return i;
			}catch (Exception e){
					logger.error("Class ExcelUtility | Method getRowContains | Exception desc : " + e.getMessage());
					throw(e);
			}
		}

	public static void writeTestResult(String testDataFileName, int i, int j, String result)
			throws IOException {

		String testDataFile = System.getProperty("user.dir").concat("\\src\\test\\resources\\"+testDataFileName);
		row = ws.getRow(i);
		Cell2 = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
		// System.out.println("Cell2: "+Cell2);
		if (Cell2 == null) {
			Cell2 = row.createCell(j);
			Cell2.setCellValue(result);
		} else {

			Cell2.setCellValue(result);
		}

		// Cell Style-boarder
		cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		Cell2.setCellStyle(cellStyle);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		Cell2.setCellStyle(cellStyle);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		Cell2.setCellStyle(cellStyle);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		Cell2.setCellStyle(cellStyle);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		Cell2.setCellStyle(cellStyle);
		// cellStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
		// cellStyle.setFillPattern(cellStyle.ALIGN_FILL);

		font = wb.createFont();
		if (result.equalsIgnoreCase("Pass")) {

			font.setColor(IndexedColors.GREEN.getIndex());

		} else {
			font.setColor(IndexedColors.RED.getIndex());

		}

		cellStyle.setFont(font);

		FileOutputStream fos = new FileOutputStream(testDataFile);
		wb.write(fos);
		fos.flush();
		fos.close();

	}

	public static void writeDataInSheet(String fileName,String sheetName, String value, int rowNum , int colNum) {

		try {


			File excel = new File(fileName);
			fis = new FileInputStream(excel);
			wb = new XSSFWorkbook(fis);
			ws = wb.getSheet(sheetName);
			row= ws.getRow(rowNum);

			if(row==null){
				System.out.println("row is "+row);
				row = ws.createRow(rowNum);
			}

			//  XSSFRow row = sheet.createRow(rowNum);

			XSSFCell cell = row.createCell(colNum);
			cell.setCellValue(value);

			// open an OutputStream to save written data into Excel file
			FileOutputStream os = new FileOutputStream(new File(fileName));
			wb.write(os);


			// Close workbook, OutputStream and Excel file to prevent leak

			os.flush();
			os.close();
			fis.close();




		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	
	/*public static void convertCsvFileToExcelFile(String csvFilePath,String execlFilePath,String sheetName) 
	{
		try{


			ArrayList arList=null;
			ArrayList al=null;

			String thisLine;
			int count=0;
			FileInputStream fis = new FileInputStream(csvFilePath);
			DataInputStream myInput = new DataInputStream(fis);
			int i=0;
			arList = new ArrayList();
			while ((thisLine = myInput.readLine()) != null)
			{
				al = new ArrayList();
				String strar[] = thisLine.split(",");
				for(int j=0;j<strar.length;j++)
				{
					al.add(strar[j]);
				}
				arList.add(al);
				System.out.println();
				i++;
			}

			try
			{
				HSSFWorkbook hwb = new HSSFWorkbook();
				HSSFSheet sheet = hwb.createSheet(sheetName);
				for(int k=0;k<arList.size();k++)
				{
					ArrayList ardata = (ArrayList)arList.get(k);
					HSSFRow row = sheet.createRow((short) 0+k);
					for(int p=0;p<ardata.size();p++)
					{
						HSSFCell cell = row.createCell((short) p);
						String data = ardata.get(p).toString();
						if(data.startsWith("=")){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							data=data.replaceAll("\"", "");
							data=data.replaceAll("=", "");
							cell.setCellValue(data);
						}else if(data.startsWith("\"")){
							data=data.replaceAll("\"", "");
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(data);
						}else{
							data=data.replaceAll("\"", "");
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
							cell.setCellValue(data);
						}

					}
					System.out.println();
				}
				FileOutputStream fileOut = new FileOutputStream(execlFilePath);
				hwb.write(fileOut);
				fileOut.close();
				fis.close();
				System.out.println("Your excel file has been generated");
			} catch ( Exception ex ) {
				ex.printStackTrace();
			} //main method ends

		}
		catch (NullPointerException nullPointerException) {
			System.out.println(" sheetname/row number/column number is null/not exists ");
		

		} 

		catch (IOException IOException) {
			// TODO: handle exception
			System.out.println("   there is a problem in reading or writing data from/to file"); 
			
		}

		catch(Exception e){
			System.out.println("exception raised while converting csv file  into xls file");
		
		}

	}
*/
	public static void updateCSV(String fileToUpdate, int col, boolean changeDate) throws IOException, Exception {

		DateFormat dddStdDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String toDay = dddStdDateFormat.format(new Date());
        System.out.println("Today date:"+toDay);

        Date tempDate = dddStdDateFormat.parse(toDay);
        Date addDays = DateUtils.addDays(tempDate, +5);
        String date = dddStdDateFormat.format(addDays);
        System.out.println("from date:"+date);
		File inputFile = new File(fileToUpdate);

		// Read existing file 
	
		CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
		List<String[]> csvBody = reader.readAll();
		int size=csvBody.size();
		String temp = sdf.format(new Date());
		String replace=temp.substring(0, 11);
		System.out.println("replace:"+replace);
	
		// get CSV row column  and replace with by using row and column
		for(int i=1;i<size;i++){
			
			csvBody.get(i)[col] = replace;
			System.out.println("i"+i);
			if(changeDate){
				
				csvBody.get(i)[24] = date;
			}
			
			reader.close();

		// Write to CSV file which is open
			CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
//			writer.writeAll(csvBody, false);
			writer.writeAll(csvBody);
			
//			writer.writeAll(csvBody);
			
			writer.flush();
			writer.close();
	
		}
		System.out.println("file write complete");
		}

	public static void updateCSV(String fileToUpdate, int col,String value) throws IOException, Exception {

		File inputFile = new File(fileToUpdate);

		// Read existing file 
	
		CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
		List<String[]> csvBody = reader.readAll();
		int a=csvBody.size();
	
		String replace=value;
		System.out.println("replace:"+replace);
	
		// get CSV row column  and replace with by using row and column
		for(int i=1;i<a;i++){
			
			csvBody.get(i)[col] = replace;
			System.out.println("i"+i);
		
			reader.close();

		// Write to CSV file which is open
			CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
//			writer.writeAll(csvBody, false);
			writer.writeAll(csvBody);
			
//			writer.writeAll(csvBody);
			
			writer.flush();
			writer.close();
	
		}
		System.out.println("file write complete");
		}

	//Ramesh
	public static void updateCsvWithDesiredStartStartingDigit(String fileToUpdate, int col,String value) throws Exception, Exception {
		
		String temp = sdf.format(new Date());
		String replace=value+temp.substring(0, 10);
		System.out.println("replace in first method "+replace);
		updateCSV(fileToUpdate,col,true);	
		
	}	
	
	public static void updateClientIDinCSV(String fileToUpdate){


	        try {

	           //Create File & Print writer
	           FileWriter fw = new FileWriter(fileToUpdate);
	           PrintWriter pw = new PrintWriter(fw);

	           //Write headers to first row
	           pw.println("ClientProjectId,PortBTN,MigrationIndicator,CustTypeIndr,BusinessName,BusAuthName,FirstName,LastName,StreetNumberPrefix ,StreetNumber,StreetNumberSuffix,StreetPrefix,StreetName,StreetType,StreetSuffix,StructType,StructInformation,UnitType,UnitInfo,SLD,Floor,City,State,Zip,DDD,FromTN,ToTN,PortDirection,WirelessAcctNum,WirelessPIN,WirelessSSN,ListingTreatment,LRN,Additional Processing Notes,Remarks");
	           String temp = sdf.format(new Date());
	   		   String replace="GIOM"+temp;
	           String  modifiedDate = replace;
	           
	            pw.println(modifiedDate+",989-465-9250,Partial,B,ABC Company,John,,,N,123,,,StNm,Dr,,,,,,,,NewYork,NY,10231,12/31/2017,989-465-9250,9250,WirelineToWireline,,,,,5418440999,,Disconnect PIPE at port");


	           
	            pw.flush();


	           pw.close();

	           //Close the File Writer
	           fw.close();
	       } catch (IOException e) {
	           System.out.println("Exception when trying to create csv File: " + e);
	       }
	   }

	//Ramesh
	public static String[][] getMultipleRowsInSingleColmnForStringData(String fileName, String sheetName, int ColNum) throws Exception {
//        int count=0;
//        String cellValue ="";
        String[] tabArray = null;
         int ci=0;
//         int cj=0;
         String[][] tabArray1 = null;


        try {
         FileInputStream file = new FileInputStream(new File(fileName));

         Workbook workbook = WorkbookFactory.create(file); 
         Sheet sheet = workbook.getSheet(sheetName);
         int lastRowNo=sheet.getLastRowNum();
         Iterator<Row> rowIterator = sheet.iterator();
     
         tabArray=new String[lastRowNo];

         while(rowIterator.hasNext()) {
          Row row = rowIterator.next();
          int rowNumber=row.getRowNum();
       
          if(!(rowNumber==0)){
         
          Iterator<Cell> cellIterator = row.cellIterator();
          while(cellIterator.hasNext()) {
           Cell cell = cellIterator.next();
           int cellNumber=cell.getColumnIndex();
      
           if(cellNumber==ColNum){
             
             String value=cell.getStringCellValue();
             
             if(!(value==""||value==null)){
                           
             tabArray[ci] =cell.getStringCellValue();
//             System.out.println("cell value :"+tabArray[ci]);
             
             ci++;
             }
             break;
            }}
          }
          }
         
         
         
         ArrayList<String> list = new ArrayList<String>();
         for (String s : tabArray)
             if (!(s==null||s=="")){
                 list.add(s);
             }
         tabArray = list.toArray(new String[list.size()]);
         
         tabArray1=new String[tabArray.length][1];
         for(int k=0;k<tabArray.length; k++){
          tabArray1[k][0]=tabArray[k];
         }

         file.close();    
        } catch (FileNotFoundException e) {
         e.printStackTrace();
        } catch (IOException ae) {
         ae.printStackTrace();
        }
        return tabArray1;
       }
	
	//Devaraj: Manually Sending PRN values
	public static void updateCSVWithPRNValues(String fileToUpdate, int col, String prn) throws IOException, Exception {

		File inputFile = new File(fileToUpdate);

		// Read existing file 
	
		CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
		List<String[]> csvBody = reader.readAll();
		int a=csvBody.size();
//		String temp = sdf.format(new Date());
		String replace=prn.substring(0, 10);
		System.out.println("replace:"+replace);
	
		// get CSV row column  and replace with by using row and column
		for(int i=1;i<a;i++){
			
			csvBody.get(i)[col] = replace;
			System.out.println("i"+i);
		
			reader.close();

		// Write to CSV file which is open
			CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
//			writer.writeAll(csvBody, false);
			writer.writeAll(csvBody);
			
//			writer.writeAll(csvBody);
			
			writer.flush();
			writer.close();
	
		}
		System.out.println("file write complete");
		}
	
}
