<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.toko.model.Item" %>
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
                                <!-- menu -->
                                Home | 
                                Categories |
                                Items |
                                Customers |
                                Orders 
                            </td>
                        </tr>
                        <tr height="*">
                            <td align="left" valign="top">
                                <!-- kontent -->
                                <br/>
                                <h4>Daftar Item</h4>
                                <p><a href="<%= request.getContextPath()%>/insert_item">Insert</a></p>
                                <table cellpadding="4">
                                    <tr>
                                        <td>No</td>
                                        <td>Name</td>
                                        <td>Price</td>
                                        <td>Category</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <%
                                        List<Item> items = (ArrayList<Item>) request.getAttribute("items");
                                        int no = 1;
                                        for (Item item : items) {
                                            out.println("<tr>");
                                            out.println("<td>"+ no++ +"</td>");
                                            out.println("<td>"+ item.getName() +"</td>");
                                            out.println("<td>"+ item.getPrice() +"</td>");
                                            out.println("<td>"+ item.getCategory().getName() +"</td>");
                                            out.println("<td>Edit | Delete</td>");
                                            out.println("</tr>");
                                        }
                                    %>
                                </table>
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