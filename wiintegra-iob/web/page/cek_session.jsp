<%
if(session.getAttribute("currentUser") == null && session.getAttribute("currentMhs") == null && session.getAttribute("currentDosen") == null){
    request.setAttribute("error", "Anda harus login terlebih dahulu!");
    request.getRequestDispatcher("page/index.jsp").include(request, response);
}
%>