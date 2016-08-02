package me.littleelephant;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    private static String fileMask = "[\\w]*-[\\d]{4}-[\\d]{2}-[\\d]{2}(-\\d+|).csv";
    private static String dateMask = "[\\d]{4}-[\\d]{2}-[\\d]{2}";
    private static String dateFormat ="yyyy-MM-dd";
    private static String taskMask = "[\\w]*-[\\d]{4}-[\\d]{2}-[\\d]{2}";

    public static void main(String[] args) throws ParseException {
	// write your code here
    HashMap params = extractAndCheckArgs(args);
    String[] tasks = prepareTasksList(params.get("path").toString(),params.get("start").toString(),params.get("end").toString());
    String[] result = TaskExecutor.executeTasks(params.get("path").toString(), tasks);
    sortAndStore(result, params.get("output").toString());
    }

    private static void sortAndStore (String[] result, String outFileName)
    {
        Arrays.sort(result);
        File fout = new File(outFileName);
        FileWriter fwr = null;
        BufferedWriter bwr = null;
        try {
            fwr = new FileWriter(fout);
            bwr = new BufferedWriter(fwr);

            for (int i = 0; i<result.length; ++i)
            {
                bwr.write(result[i]);
                bwr.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bwr.close();
                fwr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String[] prepareTasksList(String path, String start, String end) throws ParseException
    {
        String[] taskList = taskListFromFiles(path);
        return filterByDate(taskList, start,end);
    }

    private static String[] taskListFromFiles(String path) throws ParseException {
        String[] fileList = new File(path).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches(fileMask);
            }
        });
        return filesToUniqTasks(fileList);
    }

    private static String[] filesToUniqTasks(String[] list)
    {
        HashSet<String> result = new HashSet<String>();
        Pattern pat = Pattern.compile(taskMask);
        for (int i=0; i<list.length; ++i)
        {
            Matcher mat = pat.matcher(list[i]);
            if (mat.find()) {
                result.add(mat.group(0));
            }
        }
        return result.toArray(new String[0]);
    }

    private static String[] filterByDate(String[] list, String start, String end) throws ParseException {
        List<String> result = new ArrayList<String>();
        Pattern pat = Pattern.compile(dateMask);
        DateFormat format = new SimpleDateFormat(dateFormat);
        Date startDate = format.parse(start);
        Date endDate = format.parse(end);
        for (int i =0; i<list.length; ++i){
            Matcher mat = pat.matcher(list[i]);
            if (mat.find()) {
                Date date = format.parse(mat.group(0));
                if (!startDate.after(date) & !endDate.before(date)) {
                    result.add(list[i]);
                    }
                }
            }
        return result.toArray(new String[0]);
    }

    private static HashMap extractAndCheckArgs(String[] args){
        HashMap result = new HashMap();
        for (int i = 0; i < args.length; ++i) {
            if (args[i].split("=").length==2) {
                result.put(args[i].split("=")[0].toLowerCase(),args[i].split("=")[1].toLowerCase());
            }
        }
        String[] keys = {"path","start","end","output"};
        for (int i = 0; i<keys.length; ++i  ){
            if (!result.containsKey(keys[i])) {
                System.out.println("Parameter "+keys[i]+" is mandatory");
                System.exit(1);
            }
        }
        return result;
    }

}
