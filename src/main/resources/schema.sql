CREATE TABLE T_SYMBOLS (
  symbol VARCHAR(16) PRIMARY KEY,
  lot_size NUMBER(10),
  symbol_id NUMBER(5) AUTO_INCREMENT NOT NULL,
  fno_ind NUMBER(1) DEFAULT0,
  52_week_high NUMBER(10),
  52_week_low NUMBER(10)
  sector VARCHAR(10) DEFAULT NULL
);

CREATE TABLE T_SYMBOLS (
  symbol VARCHAR(16) PRIMARY KEY,
  lot_size INT,
  symbol_id INT NOT NULL,
  fno_ind BOOLEAN DEFAULT FALSE,
  week52_high NUMERIC (7, 2),
  week52_low NUMERIC (7, 2),
  sector VARCHAR(60) DEFAULT NULL
);

CREATE TABLE T_SETTING_LKP (
	SETTING_ID NUMBER(4) PRIMARY KEY,
	SETTING_CD VARCHAR(32),
	SETTING_VAL VARCHAR(512),
	SETTING_CATEGORY VARCHAR(16),
	COMMENT VARCHAR(256));

INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (1, 'BASE_URL_GET_SYMBOL_CATAGORY_LIST', 
	'https://nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm',
	'URL to retrieve intial stock catogiry list, read the stock catagory from dropdown');
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (2, 'STOCK_DATA_URL_GET_SYMBOL_LIST', 
	'https://nseindia.com/live_market/dynaContent/live_watch/stock_watch/{0}StockWatch.json',
	'URL to retrieve intial stock data list, read symbol name, 52week high/low from data table');
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (3, 'FNO_DATA_URL_GET_MARKET_LOT', 
	'https://nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuoteFO.jsp?instrument=FUTSTK&expiry={0}&type=-&strike=-&underlying=',
	'URL to retrieve lot size by symbol');
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (4, 'EQUITY_1MONTH_HIST_DATA_URL', 
	'https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?series=EQ&fromDate=undefined&toDate=undefined&datePeriod=1month&symbol=',
	'URL to retrieve last 1 month historical data for equities by symbol');
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (5, 'EQUITY_BE_1MONTH_HIST_DATA_URL', 
	'https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?series=BE&fromDate=undefined&toDate=undefined&datePeriod=1month&symbol=',
	'URL to retrieve last 1 month historical data for equities by symbol');
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (6, 'FNO_1MONTH_HIST_DATA_URL', 
	'https://nseindia.com/live_market/dynaContent/live_watch/get_quote/getFOHistoricalData.jsp?instrument=FUTSTK&type=-&strike=-&fromDate=undefined&toDate=undefined&datePeriod=1month&expiry={0}&underlying=',
	'URL to retrieve last 1 month historical data for future and options by symbol');
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (6, 'FNO_1MONTH_HIST_DATA_URL', 
	'https://nseindia.com/live_market/dynaContent/live_watch/get_quote/getFOHistoricalData.jsp?instrument=FUTSTK&type=-&strike=-&fromDate=undefined&toDate=undefined&datePeriod=3months&expiry={0}&underlying=',
	'URL to retrieve last 3 months historical data for future and options by symbol');
	
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP1_UPDT_KITE_KEY_INSTRUMENT_TOKEN_15_MINUTE', 
	'01 42 9 * * MON-FRI',
	'Every day 15 minute candle scanner init call scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP2_START_SCANNER_15_MINUTE', 
	'02 45 9 * * MON-FRI',
	'Every day 15 minute candle scanner start up scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP3_UPDT_EVERY_15_MINUTE_HIST_DATA', 
	'02 0/15 10-16 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP4_FORCE_EXIT_TRADES', 
	'01 20 15 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP5_CLOSE_TRADEING_END_OF_THE_DAY', 
	'30 29 15 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');	

INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP6_CLOSE_PLACE_ORDERS_BY_3_PM', 
	'00 59 14 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP7_CLOSE_SCANNER_RUN_ACTIVITY', 
	'59 29 15 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP1_LAST_15_HIST_DATA_TREND', 
	'02 14 9-16 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP2_LAST_15_HIST_DATA_TREND', 
	'02 29 9-16 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP3_LAST_15_HIST_DATA_TREND', 
	'02 44 9-16 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'STEP4_LAST_15_HIST_DATA_TREND', 
	'02 59 9-16 * * MON-FRI',
	'Every day 15 minute candle scanner historial data scheduler cron job');
	
INSERT INTO T_SETTING_LKP(SETTING_ID, SETTING_CD, SETTING_VAL,COMMENT)
	VALUES (7, 'TIME_ZONE', 
	'IST',
	'Tradewae Systems time zone ');
	
CREATE TABLE T_USERS (
	USER_NAME VARCHAR(32) 
	USER_ID VARCHAR(12) PRIMARY KEY,
	API_KEY VARCHAR(32) NOT NULL,
	SECRET_KEY VARCHAR(64) NOT NULL,
	ACCESS_TOKEN VARCHAR(64),
	VALID_IND BOOLEAN DEFAULT FALSE,
	ADMIN_IND BOOLEAN DEFAULT FALSE,
	SUBSCRIPT_START DATE NOT NULL,
	SUBSCRIPT_END DATE NOT NULL,
	DT_TM_STAMP DATE NOT NULL);	
INSERT INTO T_USERS (USER_ID, API_KEY, SECRET_KEY, ACCESS_TOKEN, VALID_IND, ADMIN_IND, SUBSCRIPT_START, SUBSCRIPT_END, DT_TM_STAMP)
	VALUES ('Chanti', 'AF7508', 'cwat7f1yga0ohmdf', 'zrpvqskzr1v4xi72mwjdhtmjpyeltdhq', NULL, TRUE, TRUE, SYSDATE-1, SYSDATE+3650, SYSDATE-1);
	
	