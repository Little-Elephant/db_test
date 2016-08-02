package me.littleelephant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PavelV on 01.08.2016.
 */
public class IsimStat {
    protected Float min = Float.MAX_VALUE;
    protected Float max = (float) 0;
    protected Float last = (float) 0;
    protected Date lastDate = new Date(0);
    protected Integer qty = 0;

    public void addStat (String stat) throws ParseException {
        String[] in = stat.split(",");
        qty = qty + Integer.parseInt(in[3]);
        Float currentPrice = Float.parseFloat(in[2]);
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

    public String getResult()
    {
        return last.toString()+","+max.toString()+","+min.toString()+","+qty.toString();
    }

}
