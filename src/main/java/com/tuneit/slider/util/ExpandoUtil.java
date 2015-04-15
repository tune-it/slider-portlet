package com.tuneit.slider.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

/**
 *
 * @author jek
 * @author nicola
 */
public class ExpandoUtil {

    /**
     * Get Expando table. If table is not found then it will be created
     * @param companyId Site company id
     * @param tableName Expando table name
     * @return Expando table
     * @throws PortalException
     * @throws SystemException
     */
    public static ExpandoTable getExpandoTable(long companyId, String tableName) throws PortalException, SystemException {
        try {
            return ExpandoTableLocalServiceUtil.getTable(companyId, tableName, tableName);
        } catch (NoSuchTableException nste) {
            return ExpandoTableLocalServiceUtil.addTable(companyId, tableName, tableName);   
        }
    }
    
    /**
     * 
     * @param table
     * @param columnsMap map<name,type>
     * @return
     * @throws PortalException
     * @throws SystemException
     */
    public static List<ExpandoColumn> addExpandoColumns(ExpandoTable table, Map<String, Integer> columnsMap) 
            throws PortalException, SystemException {
        List<ExpandoColumn> columns = new ArrayList<ExpandoColumn>();
        if (columnsMap != null) {
            long tableId = table.getTableId();
            for (Entry<String, Integer> c : columnsMap.entrySet()) {
                try {
                    ExpandoColumnLocalServiceUtil.getColumn(tableId, c.getKey());
            
                } catch (Exception e) {
                    ExpandoColumn column = ExpandoColumnLocalServiceUtil.addColumn(tableId, c.getKey(), c.getValue());
                    columns.add(column);
                }
            }
        }
        return columns;
    }
    

    public static void deleteRow(long companyId, String tableName, long classPK) throws PortalException, SystemException {
        ExpandoTable table = ExpandoUtil.getExpandoTable(companyId, tableName);
        ExpandoRowLocalServiceUtil.deleteRow(table.getTableId(), classPK);
    }
    
    public static void addValue(long companyId, String tableName, String columnName, long id, Object value) throws PortalException, SystemException {
        ExpandoValueLocalServiceUtil.addValue(companyId, tableName, tableName, columnName, id, value);
    }

    public static List<ExpandoRow> getAllValues(long companyId, String tableName) throws SystemException{
        return ExpandoRowLocalServiceUtil.getRows(companyId, tableName, tableName, -1, -1);
    }
    
  
}
