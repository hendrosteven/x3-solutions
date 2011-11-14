<%@page import="wii.edu.core.model.User" %>
<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="js/ext/ext-all.js"></script>

<%
    User currentUser = (User)session.getAttribute("currentUser");
    if(currentUser.getLevel() == 1){
%>
    <script type="text/javascript" src="js/wiintegra/menu_admin.js"></script>
    <div id="west"></div>
<%
    }else if(currentUser.getLevel() == 2){
%>
    <script type="text/javascript" src="js/wiintegra/menu_akademik.js"></script>
    <div id="west"></div>
<%
    }else if(currentUser.getLevel() == 3){
%>
    <script type="text/javascript" src="js/wiintegra/menu_keuangan.js"></script>
    <div id="west"></div>
<%
    }
%>