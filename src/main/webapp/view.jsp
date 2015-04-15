<%@page import="com.tuneit.slider.EffectEnum"%>
<%@page import="com.tuneit.slider.util.SliderUtil"%>

<%
/**
 * Copyright (c) 2012-15 Tune IT.
 */
%>

<%@include file="./init.jsp" %>

<div>    
    <%    
        String themeName = preferences.getValue(SliderPortlet.PROPS_THEME, "default");
    %>
    <div class="slider-wrapper theme-<%= themeName %>" style="width: 100%;">
        <div class="ribbon"></div>
        <div id="slider" class="nivoSlider">
            <%       	
                List<SliderItem> items = SliderUtil.getItems(themeDisplay.getCompanyId());
                for (SliderItem item : items) {
                    if (item.getUrl() != null) { %>
                        <a href="<%= item.getUrl() %>">
            <%      } %>	
                        <img src="<%= item.getImagePath().toString() %>" 
                            alt="<%= item.getAlt() %>"
                            title="<%= HtmlUtil.escape(item.getTitle()) %>" 
                            style="width: 100%;"
            <%      if (!item.getEffect().equals(EffectEnum.RANDOM.getName())) { %>
                            data-transition="<%= item.getEffect() %>"
            <%      } %> 
                            />
            <%      if (item.getUrl() != null) { %>
                        </a>
            <%      }
                }
            %>			
        </div>
    </div>
</div>
<script type="text/javascript">
    $(window).load(function() {
        $('#slider').nivoSlider({pauseTime: 6000});
    });
</script>