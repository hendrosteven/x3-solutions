/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import error.StreamGobbler;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Hendro Steven
 */
public class BackupRestoreWnd extends ApplicationContext {

    Textbox txtOutputFile;
    Textbox txtInputFile;
   //Input chosefile;

    public BackupRestoreWnd() {
    }

    public void onCreate() throws Exception {
        txtOutputFile = (Textbox) getFellow("txtOutputFile");
        txtInputFile = (Textbox) getFellow("txtInputFile");
        //chosefile = (Input) getFellow("chosefile");
    }

    public void backup() throws Exception {
        String path = "C:/Program Files/MySQL/MySQL Server 5.0/bin/";
        path += "mysqldump -h localhost -u root --password=admin monitoring -r ";
        path += txtOutputFile.getValue();
        Runtime rt = Runtime.getRuntime();
        rt.exec(path);
        Messagebox.show("Backup File tersimpan di " + txtOutputFile.getValue());
        //writeFile();
        txtOutputFile.setValue("");
    }

    private void writeFile() throws Exception {
        Writer output = null;
        String content = readFile(txtOutputFile.getValue());
        content = "USE monitoring;\n" + content;
        File file = new File(txtOutputFile.getValue());
        output = new BufferedWriter(new FileWriter(file));
        output.write(content);
        output.close();
        //System.out.println("Your file has been written");
    }

    private String readFile(String path) throws Exception {
        File file = new File(path);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty(
                        "line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Messagebox.show(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Messagebox.show(e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Messagebox.show(e.getMessage());
            }
        }
        return contents.toString();
    }

    public void restore() throws Exception {
        try {
            String[] path = {"cmd",
                "/c",
                "mysql -h localhost --user=root --password=admin monitoring < " + txtInputFile.getValue()};
            Process ps = Runtime.getRuntime().exec(path);
            // any error message?
            
            StreamGobbler errorGobbler = new StreamGobbler(ps.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(ps.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = ps.waitFor();
            //System.out.println("ExitValue: " + exitVal);

            Messagebox.show("Restore Database berhasil");
            txtInputFile.setValue("");
        } catch (Exception ex) {
            ex.printStackTrace();
            Messagebox.show(ex.getMessage());
        }
    }

    private static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }
        return buffer.toString();
    }

    //<h:input id="chosefile" type="file" size="50" zk:onChange="txtInputFile.value=self.value"/>
}
