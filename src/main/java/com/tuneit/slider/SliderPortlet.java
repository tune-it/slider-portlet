package com.tuneit.slider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tuneit.slider.util.ExpandoUtil;
import com.tuneit.slider.util.SliderUtil;

public class SliderPortlet extends MVCPortlet {

    private static Log log = LogFactoryUtil.getLog(SliderPortlet.class);
    
    private static final ThemeEnum[] themes = ThemeEnum.values();
    
    private static final EffectEnum[] effects = EffectEnum.values();
       
    public static final String PROPS_THEME = "theme";
    
    public static final String PROPS_IMAGE_PATH = "imagePath";
    
    public static final String PROPS_URL = "url";
    
    public static final String PROPS_TITLE = "title";
    
    public static final String PROPS_ALT = "alt";
    
    public static final String PROPS_EFFECT = "effect";
    
    public static final String PROPS_SLIDE_POSITION = "slidePosition";
    
    public static final String PROPS_ACTION = "action";
    
    public SliderPortlet() {
    }
    
    public void updatePreferences(ActionRequest req, ActionResponse res)
            throws ReadOnlyException, ValidatorException, IOException,
            Exception {
        System.out.println("1 = " + 1);
        UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(req);
        PortletSession sess = req.getPortletSession();
        String action = ParamUtil.getString(uploadRequest, PROPS_ACTION);
        System.out.println("action = " + action);
        if (action.equals(ActionEnum.SAVE.getName())) {
            save(req, uploadRequest, sess);
        } else if (action.equals(ActionEnum.ADD_ITEM.getName()) || (action.equals(ActionEnum.INSERT_ITEM.getName()))) {
            addSlide(req, uploadRequest, sess);
        } else if (action.equals(ActionEnum.DELETE_ITEM.getName())) {
            deleteSlide(req, uploadRequest, sess);
        }
    }
    
    public static void initSliderTable() {
        Map<String, Integer> columns = new HashMap<String, Integer>();
        columns.put("imagepath", ExpandoColumnConstants.STRING);
        columns.put("url".toLowerCase(), ExpandoColumnConstants.STRING);
        columns.put("title".toLowerCase(), ExpandoColumnConstants.STRING);
        columns.put("alt".toLowerCase(), ExpandoColumnConstants.STRING);
        columns.put("effect".toLowerCase(), ExpandoColumnConstants.STRING);
        
        try {
            ExpandoTable table = ExpandoUtil.getExpandoTable(SliderItem.class.getSimpleName());
            ExpandoUtil.addExpandoColumns(table, columns);
        } catch (Exception e) {
            log.error(e, e);
        }
    }
    
    public List<SliderItem> updateItemsValues(UploadPortletRequest req, List<SliderItem> items) throws ReadOnlyException, 
            ValidatorException, IOException, Exception {
        String[] imagePaths = req.getParameterValues(PROPS_IMAGE_PATH);
        String[] urls = req.getParameterValues(PROPS_URL);
        String[] titles = req.getParameterValues(PROPS_TITLE);
        String[] alts = req.getParameterValues(PROPS_ALT);
        String[] effects = req.getParameterValues(PROPS_EFFECT);
        
        SliderItem item = null;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            item.setImagePath(imagePaths[i]);
            item.setUrl(urls[i]);
            item.setTitle(HtmlUtil.unescape(titles[i]));
            item.setAlt(alts[i]);
            item.setEffect(effects[i]);
        }      
        
        return items;
    }
    
    public void save(ActionRequest req, UploadPortletRequest uploadRequest, PortletSession sess) throws Exception {
        PortletPreferences preferences = req.getPreferences();
        
        String theme = ParamUtil.getString(uploadRequest, PROPS_THEME); 
        
        List<SliderItem> items = (ArrayList<SliderItem>) sess.getAttribute("sliderItems");
        items = updateItemsValues(uploadRequest, items);
        items = SliderUtil.setItems(items);
        sess.setAttribute("sliderItems", items);
        
        preferences.setValue(PROPS_THEME, theme);
        preferences.store();     
    }
    
    public void addSlide(ActionRequest req, UploadPortletRequest uploadRequest, PortletSession sess) throws Exception {        
        List<SliderItem> items = (ArrayList<SliderItem>) sess.getAttribute("sliderItems");
        items = updateItemsValues(uploadRequest, items);
        
        int i = ParamUtil.getInteger(uploadRequest, PROPS_SLIDE_POSITION);
        if (i < 0) {
            items.add(new SliderItem());
        } else {
            items.add(i, new SliderItem());
        }
        sess.setAttribute("sliderItems", items);    
    }
    
    public void deleteSlide(ActionRequest req, UploadPortletRequest uploadRequest, PortletSession sess) throws Exception {        
        List<SliderItem> items = (ArrayList<SliderItem>) sess.getAttribute("sliderItems");
        int i = ParamUtil.getInteger(uploadRequest, PROPS_SLIDE_POSITION);
        items = updateItemsValues(uploadRequest, items);
        SliderItem removedItem = items.remove(i);
        try {
            ExpandoUtil.deleteRow(SliderItem.class.getSimpleName(), removedItem.getClassPK());
        } catch (Exception e) {
            log.debug("Removed slide not found in database.");
        }
        sess.setAttribute("sliderItems", items);
    }
    
    public static ThemeEnum[] getThemes() {
        return themes;
    }
    
    public static EffectEnum[] getEffects() {
        return effects;
    }
    
}
