package me.littleelephant;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PavelV on 31.07.2016.
 */
public class TaskExecutor {

    public static String[] executeTasks(String path, String[] list)
    {
        List<String> result = new ArrayList<String>();
        for (int i =0; i<list.length; ++i){
            result.addAll(calculateFromFiles(path, list[i]));
        }
    return result.toArray(new String[0]);
    }

    private static String[] getFilesByTask(String path, final String task)
    {
        String[] fileList = new File(path).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches(task+".*");
            }
        });
        return fileList;
    }

    private static ArrayList<String> calculateFromFiles(String path, String task)
    {
        Float min = Float.MAX_VALUE;
        Float max = (float) 0;
        Float last = (float) 0;
        Date lastDate = new Date(0);
        Integer qty = 0;
        String str;
        ArrayList<String> result= new ArrayList<String>();

        String[] files = getFilesByTask(path, task);
        for (int i=0; i<files.length; ++i){
            File file = new File(path+files[i]);
            FileReader fis = null;
            BufferedReader bis = null;

            try {
                fis = new FileReader(file);
                bis = new BufferedReader(fis);
                while ((str=bis.readLine())!=null) {
                    System.out.println(str);


                    //Work there


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
        result.add(task.split("-",2)[0]+","+task.split("-",2)[1]+","+last+","+max+","+min+","+qty);
        return result;
    }

}
