package me.littleelephant;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PavelV on 01.08.2016.
 */
public class IsimStat {
    protected static Float min = Float.MAX_VALUE;
    protected static Float max = (float) 0;
    protected static Float last = (float) 0;
    protected static Date lastDate = new Date(0);
    protected static Integer qty = 0;

    public void addStat (String stat) throws ParseException {
        String[] in = stat.split(",");
        qty = qty + Integer.parseInt(in[3]);
        Float currentPrice = Float.parseFloat(in[2]);
        System.out.println(in[1].split("\\.").length);
        while (in[1].split("\\.")[1].length()<3){in[1]=in[1]+"0";} //milleseconds must have 3 digits
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date currentDate = format.parse("1970-01-01 "+in[1]);
        if (currentPrice > max) {max = currentPrice;}
        if (currentPrice < min) {min = currentPrice;}
        if (currentDate.after(lastDate)) {
            lastDate = currentDate;
            last = currentPrice;
        }

    }

    public static String getResult()
    {
        return last.toString()+","+max.toString()+","+min.toString()+","+qty.toString();
    }

}
