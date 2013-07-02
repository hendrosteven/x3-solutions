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
            </tr>
            <tr height="*" style="background-color: #ccccff">
                <td align="center" valign="middle">
                    <form action="<%= request.getContextPath() %>/login" method="post">
                        <table cellpadding="4" width="30%" style="background-color: #cccccc">
                            <tr>
                                <td colspan="3"><h4>Admin Login</h4></td>
                            </tr>
                            <tr>
                                <td>Username</td>
                                <td>:</td>
                                <td><input type="text" size="30" name="txtuser"/></td>
                            </tr>
                            <tr>
                                <td>Password</td>
                                <td>:</td>
                                <td><input type="password" size="30" name="txtpass"/></td>
                            </tr>
                            <tr>
                                <td colspan="3"><input type="submit" value="Login"/></td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
            <tr height="5%%">
                <td align="center" style="color: #fff;">
                    <small>Training Java &copy; 2013</small>
                </td>
            </tr>
        </table>
    </body>
</html>