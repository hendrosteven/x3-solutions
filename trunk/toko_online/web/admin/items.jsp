<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.toko.model.Item" %>
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
                                            out.println("<td>" + no++ + "</td>");
                                            out.println("<td>" + item.getName() + "</td>");
                                            out.println("<td>" + item.getPrice() + "</td>");
                                            out.println("<td>" + item.getCategory().getName() + "</td>");
                                            out.println("<td>");
                                            out.println("<input type='button' id='edit" + item.getId() + "' value='E' onclick='edit(" + item.getId() + ")'>");
                                            out.println("<input type='button' id='delete" + item.getId() + "' value='X' onclick='hapus(" + item.getId() + ")'>");
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

        <div id="edit-form" title="Edit Item">
            <p class="validateTips">All form fields are required.</p>
            <form>
                <fieldset>
                    <label for="name">Name</label><br/>
                    <input size="40" type="text" name="name" id="name" 
                           class="text ui-widget-content ui-corner-all" /><br/>
                    <label for="name">Description</label><br/>
                    <textarea cols="60" rows="7" name="description" id="description"></textarea><br/>
                    <label for="name">Price</label><br/>
                    <input type="text" name="price" id="price" 
                           class="text ui-widget-content ui-corner-all" /><br/>
                    <label for="name">Category</label><br/>
                    <select name="category" id="category">
                        <%
                            List<Category> categories =
                                    (ArrayList<Category>) request.getAttribute("categories");
                            for (Category cat : categories) {
                                out.println("<option value='" + cat.getId() + "'>" + cat.getName() + "</option>");
                            }
                        %>
                    </select>
                    <br/>
                    <input type="hidden" name="id-item" id="id-item"/>
                </fieldset>
            </form>
        </div>
    </body>
    <script>
        
        $('#edit-form').dialog({
            autoOpen : false,
            height : 470,
            width : 600,
            modal : true,
            buttons : {
                "Update" : function(){
                    var _id = $('#id-item').val();
                    var _name = $('#name').val();
                    var _description = $('#description').val();
                    var _price = $('#price').val();
                    var _category = $('#category').val();
                    $.post("./update_item",{id:_id,name:_name,description:
                            _description,price:_price,category:_category},
                    function(out){
                         if(out=="true"){
                            $('#edit-form').dialog('close');
                            window.location = "./items";
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
            $.get("./get_item_by_id",{id:_id},function(output){
                var obj = $.parseJSON(output);
                $('#name').val(obj.name);
                $('#description').val(obj.description);
                $('#price').val(obj.price);
                $('#id-item').val(obj.id);
            });
            $('#edit-form').dialog('open');
        }
        
        function hapus(_id)
        {           
            $.get("./delete_item",{id:_id},function(output){
                if(output=='true'){
                    alert('data terhapus');
                }else{
                    alert('data gagal dihapus');
                }
                window.location = "./items";
            });
        }
    </script>
</html>