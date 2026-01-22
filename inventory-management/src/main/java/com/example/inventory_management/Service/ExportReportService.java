package com.example.inventory_management.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.inventory_management.DTO.ReportsDTO;

@Service
public class ExportReportService {

	public byte[] generateExcelReport(List<ReportsDTO> reports) throws IOException {
		
			try(Workbook WB=new XSSFWorkbook() ;
				ByteArrayOutputStream out=new ByteArrayOutputStream()){
				
				CellStyle dateCell=WB.createCellStyle();
				CreationHelper helper=WB.getCreationHelper();
				dateCell.setDataFormat(helper.createDataFormat().getFormat("dd-MM-yyyy"));
				
				Sheet sheet=WB.createSheet();
			    
				
				Row HeaderRow = sheet.createRow(0);
				String[] column= {"Date","Product Name","Stock In","Stock out","Available Stock"};
				for(int i=0;i<column.length;i++) {
					Cell cell=HeaderRow.createCell(i);
					cell.setCellValue(column[i]);
				}
				
				Integer rowIdx=1;
				for(ReportsDTO x:reports) {
					Row row=sheet.createRow(rowIdx++);
					Cell dataCellValue=row.createCell(0);
					
					if (x.getDate() != null) {
			            dataCellValue.setCellValue(x.getDate());
			            dataCellValue.setCellStyle(dateCell);
			        }			
					
					row.createCell(1).setCellValue(x.getProduct_Name());
					row.createCell(2).setCellValue(x.getStockInSafe());
					row.createCell(3).setCellValue(x.getStockOutSafe());
					row.createCell(4).setCellValue(x.getAvailableStockSafe());
				}
				
				for (int i = 0; i < column.length; i++) {
	                sheet.autoSizeColumn(i);
	            }
			 WB.write(out);
			 return out.toByteArray();
		}
	}

	
	
  

}
