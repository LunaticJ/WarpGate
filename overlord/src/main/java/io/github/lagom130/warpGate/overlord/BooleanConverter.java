package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class BooleanConverter implements Converter<Boolean> {
  @Override
  public Class<?> supportJavaTypeKey() {
    return Converter.super.supportJavaTypeKey();
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return Converter.super.supportExcelTypeKey();
  }


  @Override
  public Boolean convertToJavaData(ReadConverterContext<?> context) throws Exception {
    return switch (context.getReadCellData().getStringValue()) {
      case "是" -> true;
      case "否" -> false;
      default -> null;
    };
  }


  @Override
  public WriteCellData<?> convertToExcelData(WriteConverterContext<Boolean> context) throws Exception {
    return new WriteCellData<>(Boolean.TRUE.equals(context.getValue()) ? "是":"否");
  }
}
