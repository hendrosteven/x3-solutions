<%
    if(session.getAttribute("currentUser") != null){
%>
    <%@ include file="menu_user.jsp" %>
<%
    }else if(session.getAttribute("currentDosen") != null){
%>
    <%@ include file="menu_dosen.jsp" %>
<%
    }else if(session.getAttribute("currentMhs") != null){
%>
    <%@ include file="menu_mhs.jsp" %>
<%
    }
%>