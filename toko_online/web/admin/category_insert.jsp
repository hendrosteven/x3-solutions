<%@page import="com.toko.model.Category" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>

<html>
    <head>
        <title>.: Toko Online Admin :.</title>
    </head>
    <body style="margin-top: 0px;margin-left: 0px;margin-right: 0px;
          margin-bottom: 0px;font-family: sans-serif,monospace;
          font-size: 0.8em">
        <table width="100%" height="100%" cellpadding="8" style="background-color: #003399">
            <tr height="10%" style="background-color: #003399">
                <td align="left" valign="middle" style="color: #fff;">
                    <h2>Toko Online Administration</h2>
                </td>
                <td align="right" valign="bottom">
                    <a href="<%= request.getContextPath()%>/logout" style="color: #fff;text-decoration: none">Logout</a>
                </td>
            </tr>
            <tr height="*" style="background-color: #ccccff">
                <td align="left" valign="top" colspan="2">
                    <table width="100%" height="100%" cellpading="2">
                        <tr height="2%">
                            <td style="color: #000;">
                              <jsp:include page="menu.jsp"></jsp:include>
                            </td>
                        </tr>
                        <tr height="*">
                            <td align="left" valign="top">
                                <!-- kontent -->
                                <br/>
                                <h4>Input New Category</h4>
                                <form action="<%= request.getContextPath()%>/save_category" method="post">
                                    <table cellpadding="4">
                                        <tr>
                                            <td>Category Name</td>
                                            <td>:</td>
                                            <td><input type="text" name="categoryname" size="60"/></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"><input type="submit" value="Save"/></td>
                                        </tr>
                                    </table>
                                </form>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr height="5%">
                <td align="center" colspan="2" style="color: #fff;">
                    <small>Training Java &copy; 2013</small>
                </td>
            </tr>
        </table>
    </body>
</html>