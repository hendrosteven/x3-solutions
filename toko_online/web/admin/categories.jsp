<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.toko.model.Category" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>

<html>
    <head>
        <title>.: Toko Online Admin :.</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/asset/css/jquery-ui.css">
        <script src="<%= request.getContextPath()%>/asset/js/jquery.js" 
        type="text/javascript"></script>
        <script src="<%= request.getContextPath()%>/asset/js/jquery-ui.js"
        type="text/javascript">  </script>    

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
                                <jsp:include page="menu.jsp"></jsp:include>
                            </td>
                        </tr>
                        <tr height="*">
                            <td align="left" valign="top">
                                <!-- kontent -->
                                <br/>
                                <h4>Daftar Categories</h4>
                                <p><a href="<%= request.getContextPath()%>/insert_category">Insert</a></p>
                                <table cellpadding="4">
                                    <tr>
                                        <td>No</td>
                                        <td>Name</td>                                        
                                        <td>&nbsp;</td>
                                    </tr>
                                    <%
                                        List<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
                                        int no = 1;
                                        for (Category categroy : categories) {
                                            out.println("<tr>");
                                            out.println("<td>" + no++ + "</td>");
                                            out.println("<td>" + categroy.getName() + "</td>");
                                            out.println("<td>");
                                            out.println("<input type='button' id='edit" + categroy.getId() + "' value='E' onclick='edit(" + categroy.getId() + ")'>");
                                            out.println("<input type='button' id='delete" + categroy.getId() + "' value='X' onclick='hapus(" + categroy.getId() + ")'>");
                                            out.println("</td>");
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

        <div id="edit-form" title="Edit Category">
            <p class="validateTips">All form fields are required.</p>
            <form>
                <fieldset>
                    <label for="name">Name</label>
                    <input type="text" name="name" id="name" 
                           class="text ui-widget-content ui-corner-all" />
                    <input type="hidden" name="id-category" id="id-category"/>
                </fieldset>
            </form>
        </div>

    </body>
    <script>
        $('#edit-form').dialog({
            autoOpen : false,
            height : 230,
            width : 230,
            modal : true,
            buttons : {
                "Update" : function(){
                    var _name = $('#name').val();
                    var _id = $('#id-category').val();
                    $.post("./update_category",{id:_id,name:_name},function(out){
                        if(out=='true'){
                            $('#edit-form').dialog('close');
                            window.location = "./categories";
                        }else{
                            alert('data gagal diupdate');
                        }
                    });
                },
                "Cancel" : function(){
                    $(this).dialog('close');
                }
            }
        });
        
        function edit(_id){
            $.get("./get_category_by_id",{id:_id},function(output){
                $('#name').val(output);
                $('#id-category').val(_id);
            });
            $('#edit-form').dialog('open');
        }
        
        function hapus(_id)
        {           
            $.get("./delete_category",{id:_id},function(output){
                if(output=='true'){
                    alert('data terhapus');
                }else{
                    alert('data gagal dihapus');
                }
                window.location = "./categories";
            });
        }
    </script>
</html>
