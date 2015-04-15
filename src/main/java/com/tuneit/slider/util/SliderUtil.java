package com.tuneit.slider.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

import com.tuneit.slider.SliderItem;

public class SliderUtil {

    private static Log log = LogFactoryUtil.getLog(SliderUtil.class);
    
    public static List<SliderItem> getItems() {
        try {
            ExpandoTable table = ExpandoUtil.getExpandoTable(SliderItem.class.getSimpleName());
            
            Map<String, Long> columnIds = new HashMap<String, Long>();
            
            // First symbol has upper case because I invoke methods "set".
            String[] names = {"ImagePath", "Url", "Title", "Alt", "Effect"};
            for (String n : names) {
                columnIds.put(n, ExpandoColumnLocalServiceUtil.getColumn(table.getTableId(), n.toLowerCase()).getColumnId());
            }
                            
            List<ExpandoRow> values = ExpandoUtil.getAllValues(SliderItem.class.getSimpleName());
            ExpandoValue value = null;
            List<SliderItem> items = new ArrayList<SliderItem>();
            for (ExpandoRow row : values) {
                long rid = row.getRowId();
                SliderItem item = new SliderItem();
                for (Entry<String, Long> cid : columnIds.entrySet()) {
                    value = ExpandoValueLocalServiceUtil.getValue(cid.getValue(), rid);
                    Method method = item.getClass().getMethod("set" + cid.getKey(), String.class);
                    method.invoke(item, value.getString());
                }
                item.setClassPK(value.getClassPK());
                items.add(item);
            }
            
            return items;
        } catch (NoSuchColumnException nsce) {
            log.info("Columns not found.");
            return new ArrayList<SliderItem>();
        } catch (Exception e) {
            log.error(e, e);
            return new ArrayList<SliderItem>();
        }
    }
    
    /**
     * 
     * @param items
     * @return saved sliders 
     */
    public static List<SliderItem> setItems(List<SliderItem> items) {
        List<SliderItem> result = new ArrayList<SliderItem>();
        if (items != null && items.size() > 0) {
            for (SliderItem item : items) {
                if (!item.isEmpty()) {
                    try {
                        // store to expando table
                        ExpandoTable table = ExpandoUtil.getExpandoTable(SliderItem.class.getSimpleName());
                        long classPK = item.getClassPK();
                        ExpandoUtil.addValue(table.getName(), "imagepath", classPK, item.getImagePath());
                        ExpandoUtil.addValue(table.getName(), "url", classPK, item.getUrl());
                        ExpandoUtil.addValue(table.getName(), "title", classPK, item.getTitle());
                        ExpandoUtil.addValue(table.getName(), "alt", classPK, item.getAlt());
                        ExpandoUtil.addValue(table.getName(), "effect", classPK, item.getEffect());
                        
                        result.add(item);
                    } catch (Exception e) {
                        log.error(e, e);
                    }
                }
            }
        }
        return result;
    }
}
