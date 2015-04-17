<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tuneit.slider.util.SliderUtil"%>
<%@page import="com.tuneit.slider.EffectEnum"%>
<%@page import="com.tuneit.slider.ActionEnum"%>
<%@page import="com.tuneit.slider.ThemeEnum"%>
<%@page import="javax.portlet.PortletURL"%>

<%
    /**
     * Copyright (c) 2011-15 Tune IT.
     */
%>

<%@ include file="./init.jsp"%>

<%
    SliderPortlet.initSliderTable(themeDisplay.getCompanyId());
    String themeName = preferences.getValue(SliderPortlet.PROPS_THEME, ThemeEnum.DEFAULT.getName());
%>

<portlet:actionURL name="updatePreferences" var="updatePreferencesURL" />
<aui:form action="<%= updatePreferencesURL %>" enctype="multipart/form-data" method="post" name="preferencesForm">
    <aui:fieldset label="Global settings">
        <aui:layout>
            <aui:column>
                <aui:select name="<%= SliderPortlet.PROPS_THEME %>" label="Theme">
                    <%	
                        ThemeEnum[] themes = SliderPortlet.getThemes();
                        for (ThemeEnum t : themes) {
                    %>
                            <aui:option selected="<%= themeName.equals(t.getName()) %>" value="<%= t.getName() %>">
                                <%= t.getName() %>
                            </aui:option>
                    <%	
                        }
                    %>
                </aui:select>
            </aui:column>
        </aui:layout>

        <div style="float: left;">
            <aui:button type="submit" value="Save all" />
        </div>

        <div style="float: right;">
            <a href="<%= portletDisplay.getURLBack() %>" class="portlet-icon-back" />Return to Full page</a>
        </div>
    </aui:fieldset>
	
    <aui:fieldset label="Slides">
        <liferay-ui:panel-container extended="true" persistState="true" accordion="true" >	
            <%
                List<SliderItem> items = (ArrayList<SliderItem>) portletSession.getAttribute("sliderItems");
                if (items == null) {
                    items = SliderUtil.getItems(themeDisplay.getCompanyId());
                    portletSession.setAttribute("sliderItems", items);
                }

                int i = 0;
                for (SliderItem item : items) {
            %>
                    <liferay-ui:panel id='<%= "slider-panel-" + i %>' cssClass="slider-panel" title='<%= "Slide " + (i + 1)%>' 
                                      collapsible="true" extended="true" >

                        <div style="float: right;">
                            <div>
                                <aui:a href="#" onclick='<%= renderResponse.getNamespace() + "insertSlide(" + i + ");" %>' label="Insert slide" />
                            </div>
                            <div>
                                <aui:a href="#" onclick='<%= renderResponse.getNamespace() + "deleteSlide(" + i + ");" %>' label="Delete slide" />
                            </div>
                        </div>

                        <% if (item.getImagePath() != null && !item.getImagePath().isEmpty()) { %>
                            <img src="<%= item.getImagePath() %>" alt="<%= item.getAlt() %>" title="<%= item.getTitle() %>" style="width: 100%;" class="img-preview"/>
                        <% } %>
                        <aui:input name="<%= SliderPortlet.PROPS_IMAGE_FILE %>" label="Image file" type="file" />
                        <aui:input name="<%= SliderPortlet.PROPS_URL %>" label="Image link" value="<%= item.getUrl() %>" />				
                        <aui:input name="<%= SliderPortlet.PROPS_TITLE %>" label="Title" value="<%= HtmlUtil.escape(item.getTitle()) %>" />
                        <aui:input name="<%= SliderPortlet.PROPS_ALT %>" label="Alt" value="<%= item.getAlt() %>" />
                        <aui:select name="<%= SliderPortlet.PROPS_EFFECT %>" label="Effect">
                            <%	
                                EffectEnum[] effects = SliderPortlet.getEffects();
                                for (EffectEnum e : effects) {
                            %>
                                    <aui:option selected="<%= item.getEffect().equals(e.getName()) %>" value="<%= e.getName() %>">
                                        <%= e.getName() %>
                                    </aui:option>
                            <%	
                                }
                            %>
                        </aui:select>

                    </liferay-ui:panel>
            <%	
                    i++;
                }
            %>
        </liferay-ui:panel-container>

        <div style="padding: 10px;">
            <aui:a href="#" cssClass="big-link" onclick='<%= renderResponse.getNamespace() + "addSlide();" %>' label="Add slide" />
        </div>
    </aui:fieldset>
        
    <aui:input type="hidden" name="<%= SliderPortlet.PROPS_ACTION %>" value="<%= ActionEnum.SAVE.getName() %>"/>
    <aui:input type="hidden" name="<%= SliderPortlet.PROPS_SLIDE_POSITION %>" value="<%= 0 %>"/>	
</aui:form>

<script type="text/javascript">
    function <portlet:namespace />addSlide() {
        document.<portlet:namespace />preferencesForm.<portlet:namespace /><%= SliderPortlet.PROPS_ACTION %>.value = "<%= ActionEnum.ADD_ITEM.getName() %>";
        document.<portlet:namespace />preferencesForm.<portlet:namespace /><%= SliderPortlet.PROPS_SLIDE_POSITION %>.value = -1;
        submitForm(document.<portlet:namespace />preferencesForm);
    }

    function <portlet:namespace />insertSlide(i) {
        document.<portlet:namespace />preferencesForm.<portlet:namespace /><%= SliderPortlet.PROPS_ACTION %>.value = "<%= ActionEnum.INSERT_ITEM.getName() %>";
        document.<portlet:namespace />preferencesForm.<portlet:namespace /><%= SliderPortlet.PROPS_SLIDE_POSITION %>.value = i;
        submitForm(document.<portlet:namespace />preferencesForm);
    }

    function <portlet:namespace />deleteSlide(i) {
        document.<portlet:namespace />preferencesForm.<portlet:namespace /><%= SliderPortlet.PROPS_ACTION %>.value = "<%= ActionEnum.DELETE_ITEM.getName() %>";
        document.<portlet:namespace />preferencesForm.<portlet:namespace /><%= SliderPortlet.PROPS_SLIDE_POSITION %>.value = i;
        submitForm(document.<portlet:namespace />preferencesForm);
    }
</script>