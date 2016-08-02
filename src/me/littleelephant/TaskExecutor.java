package me.littleelephant;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by PavelV on 31.07.2016.
 */
public class TaskExecutor {

    public static String[] executeTasks(String path, String[] list) throws ParseException {
        List<String> result = new ArrayList<String>();
        for (int i =0; i<list.length; ++i){
            result.addAll(calculateFromFiles(path, list[i]));
        }
    return result.toArray(new String[0]);
    }

    private static String[] getFilesByTask(String path, final String task)
    {
        return new File(path).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches(task+".*");
            }
        });
    }

    private static ArrayList<String> calculateFromFiles(String path, String task) throws ParseException {
        String str;
        ArrayList<String> result= new ArrayList<String>();
        HashMap statByIsim = new HashMap();

        String[] files = getFilesByTask(path, task);
        for (int i=0; i<files.length; ++i){
            File file = new File(path+files[i]);
            FileReader fis = null;
            BufferedReader bis = null;

            try {
                fis = new FileReader(file);
                bis = new BufferedReader(fis);
                while ((str=bis.readLine())!=null) {
                    String isim = str.split(",")[0];
                    if (statByIsim.get(isim) == null) {statByIsim.put(isim, new IsimStat());}
                    IsimStat isTmp = (IsimStat) statByIsim.get(isim);
                    isTmp.addStat(str);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    bis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        for (Object key: statByIsim.keySet()) {
            IsimStat isTmp = (IsimStat) statByIsim.get(key);
            result.add(task.split("-",2)[0]+","+task.split("-",2)[1]+","+key.toString()+","+isTmp.getResult());
        }
        return result;
    }

}
