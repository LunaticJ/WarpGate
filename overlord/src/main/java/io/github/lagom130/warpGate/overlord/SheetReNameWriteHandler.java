package io.github.lagom130.warpGate.overlord;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

public class SheetReNameWriteHandler implements SheetWriteHandler {

  public SheetReNameWriteHandler() {
  }

  public static SheetReNameWriteHandler create() {
    return new SheetReNameWriteHandler();
  }

  @Override
  public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    String sheetName = writeSheetHolder.getSheetName();
    sheetName = StringUtils.isEmpty(sheetName) ? writeSheetHolder.getCachedSheet().getSheetName() : sheetName;
    Integer sheetNo = writeSheetHolder.getSheetNo();
    sheetNo = sheetNo == null ? 0 : sheetNo;
    writeWorkbookHolder.getCachedWorkbook().setSheetName(sheetNo, sheetName);
  }
}
