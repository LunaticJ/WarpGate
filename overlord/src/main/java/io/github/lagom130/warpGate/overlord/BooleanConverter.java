package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class BooleanConverter implements Converter {
  @Override
  public Class<?> supportJavaTypeKey() {
    return Converter.super.supportJavaTypeKey();
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return Converter.super.supportExcelTypeKey();
  }

  @Override
  public WriteCellData<?> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
    return Converter.super.convertToExcelData(value, contentProperty, globalConfiguration);
  }

  @Override
  public WriteCellData<?> convertToExcelData(WriteConverterContext context) throws Exception {
    return Converter.super.convertToExcelData(context);
  }

  @Override
  public Object convertToJavaData(ReadConverterContext context) throws Exception {
    return Converter.super.convertToJavaData(context);
  }

  @Override
  public Object convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
    return Converter.super.convertToJavaData(cellData, contentProperty, globalConfiguration);
  }
}
