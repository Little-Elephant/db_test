Parameter example:
path=c:\test\testdata\ start=2016-06-19 end=2016-06-22 output=./out.txt

All parameters are mandatory

Task
----
There are several financial markets. Each market has a number of tradable financial instruments identified by ISIN. Any ISIN can be traded at multiple exchanges. After close of business markets publish flat files with all trade events for the day. Need to implement a tool which parses output files from multiple markets for a given time period and prints per-ISIN close price for every day, where close price is a price of the very last trade event for particular ISIN, high/low prices for a day and total volume of contracts traded (quantity column). Tool should ignore files out of a given time period.

Minimum requirement: implement single-threaded Java console application to process input files.

Preferred requirement: implement multi-threaded Java console application using buffered input streams to avoid IO contention at some degree. Candidate should decide on shared data structures and which logic should be run in parallel.

Input
-----
All output files are stored in a flat file directory.

File name format: <market>-<yyyy-mm-dd>[-<subindex>].csv

Examples of directory structure with valid file names:

C:

   test

      testdata

         eurex-2016-06-20.csv
         xetra-2016-06-09-1.csv
         xetra-2016-06-09-2.csv
         moex-2016-06-10.csv



CSV columns:

ISIN,time(hh:mm:ss.mi),price,quantity



Examples:
RU000A0J2Q06,16:00:00.00,339.00,1000
RU0007661625,17:00:00.00,143.52,100
RU0009029540,17:19:00.990,134.52,100
RU0007661625,17:58:12.00,145.52,100
RU0009029540,17:59:59.000,135.50,200
RU000A0J2Q06,17:59:00.00,340.00,2000

Time order of trade event records is NOT guaranteed.

Program parameters:

<input directory patch> <output file path> <period start> <period end>

Example:

c:/test/testdata c:/test/testdata.out 2016-06-10 2016-06-20

Output
------
Save to output file close/high/low prices and volume ordered by exchange, date and ISIN.

Example:

eurex,2016-06-20,EU0000100010,101.01,101.00,97.9,100000

moex,2016-06-10,RU0007661625,145.52,145.52,143.52,200

moex,2016-06-10,RU0009029540,135.50,135.50,134.52,300

moex,2016-06-10,RU000A0J2Q06,340.00,340.00,339.00,3000

