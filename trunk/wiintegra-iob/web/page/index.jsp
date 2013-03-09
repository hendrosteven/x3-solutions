<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>.: Academic Information System (DEMO) :.</title>
        <link href="page/images/style.css" rel="stylesheet" type="text/css">
        <style>
             .body{
                background-image: url('page/images/bg.jpg');
                background-size: cover;
                font-family: sans-serif;
                font-size: 11pt;
            }
        </style>
    </head>
    <body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" class="body">        
        <br><br><br><br><br>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="35%">&nbsp;</td>
                <td>&nbsp;</td>
                <td width="35%">&nbsp;</td>
            </tr>
            <tr>
                <td width="35%">&nbsp;</td>
                <td>
<%
if(request.getAttribute("error") != null){
    out.println("<h2><center>"+request.getAttribute("error")+"</center></h2>");
    request.setAttribute("error", null);
}
%>
                </td>
                <td width="35%">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                <!--td valign="middle" align="center"-->
                    <form action="login" method="post">
                        <table width="100%" border="0" cellspacing="0" cellpadding="4">
                            <tr>
                                <td colspan="2" bgcolor="#FFFFFF"><img src="page/images/login.gif" width="74" height="33"></td>
                            </tr>
                            <tr>
                                <td width="38%" rowspan="6" align="center" valign="middle" bgcolor="#F1F3F5"><img src="page/images/security.png" width="64" height="64"></td>
                                <td width="62%" bgcolor="#F1F3F5">User ID: </td>
                            </tr>
                            <tr>
                                <td bgcolor="#F1F3F5"><input type="text" name="userid" size="20"/></td>
                            </tr>
                            <tr>
                                <td bgcolor="#F1F3F5">Password:</td>
                            </tr>
                            <tr>
                                <td bgcolor="#F1F3F5"><input type="password" name="password" size="20"/></td>
                            </tr>
                            <tr>
                                <td bgcolor="#F1F3F5">Login as:</td>
                            </tr>
                            <tr>
                                <td bgcolor="#F1F3F5">
                                    <select name="sebagai">
                                        <option value="1">Mahasiswa</option>
                                        <option value="2">Dosen</option>
                                        <option value="3">Sys Admin</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" bgcolor="#F1F3F5" align="center"><input type="submit" name="submit" value="Login" /></td>
                            </tr>
                        </table>
                    </form>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="center"><p>
                        <small>Sistem Informasi Akademik<br>
                            <br>
                        &copy; Universitas Demo Version</small>
                </p></td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </body>
</html>