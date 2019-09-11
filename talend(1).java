package etl_demo_edureka.j_1_etl_hands_on_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.TalendDate;
import routines.Relational;
import routines.Mathematical;
import routines.SQLike;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")
/**
 * Job: J_1_ETL_HANDS_ON Purpose: to provide insight in ETL job<br>
 * Description: to provide insight in ETL job <br>
 * @author user@talend.com
 * @version 6.5.1.20180116_1512
 * @status DEV
 */
public class J_1_ETL_HANDS_ON implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "J_1_ETL_HANDS_ON.log");
	}
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(J_1_ETL_HANDS_ON.class);

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset
			.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

	}

	private ContextProperties context = new ContextProperties();

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "J_1_ETL_HANDS_ON";
	private final String projectName = "ETL_DEMO_EDUREKA";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	public void setDataSources(
			java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources
				.entrySet()) {
			talendDataSources.put(
					dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry
							.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(
			new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent,
				final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null
						&& currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE",
							getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE",
						getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent
						+ " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					J_1_ETL_HANDS_ON.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass()
							.getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(J_1_ETL_HANDS_ON.this, new Object[] { e,
									currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputDelimited_1_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tFileOutputDelimited_1_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tFileOutputDelimited_2_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tFileInputDelimited_2_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tAdvancedHash_row2_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread
				.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(),
				ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_2_onSubJobError(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread
				.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(),
				ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class products_outStruct implements
			routines.system.IPersistableRow<products_outStruct> {
		final static byte[] commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];
		static byte[] commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];

		public Integer Product_id;

		public Integer getProduct_id() {
			return this.Product_id;
		}

		public String Product_name;

		public String getProduct_name() {
			return this.Product_name;
		}

		public Integer Sales_Rating;

		public Integer getSales_Rating() {
			return this.Sales_Rating;
		}

		public Integer Total_expenditure;

		public Integer getTotal_expenditure() {
			return this.Total_expenditure;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos)
				throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length) {
					if (length < 1024
							&& commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length == 0) {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[1024];
					} else {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[2 * length];
					}
				}
				dis.readFully(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length);
				strReturn = new String(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON) {

				try {

					int length = 0;

					this.Product_id = readInteger(dis);

					this.Product_name = readString(dis);

					this.Sales_Rating = readInteger(dis);

					this.Total_expenditure = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Product_id, dos);

				// String

				writeString(this.Product_name, dos);

				// Integer

				writeInteger(this.Sales_Rating, dos);

				// Integer

				writeInteger(this.Total_expenditure, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_id=" + String.valueOf(Product_id));
			sb.append(",Product_name=" + Product_name);
			sb.append(",Sales_Rating=" + String.valueOf(Sales_Rating));
			sb.append(",Total_expenditure=" + String.valueOf(Total_expenditure));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_id == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_id);
			}

			sb.append("|");

			if (Product_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_name);
			}

			sb.append("|");

			if (Sales_Rating == null) {
				sb.append("<null>");
			} else {
				sb.append(Sales_Rating);
			}

			sb.append("|");

			if (Total_expenditure == null) {
				sb.append("<null>");
			} else {
				sb.append(Total_expenditure);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(products_outStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class Products_rejectStruct implements
			routines.system.IPersistableRow<Products_rejectStruct> {
		final static byte[] commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];
		static byte[] commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];

		public Integer Product_id;

		public Integer getProduct_id() {
			return this.Product_id;
		}

		public String Product_name;

		public String getProduct_name() {
			return this.Product_name;
		}

		public Integer Sales_Rating;

		public Integer getSales_Rating() {
			return this.Sales_Rating;
		}

		public Integer Total_expenditure;

		public Integer getTotal_expenditure() {
			return this.Total_expenditure;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos)
				throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length) {
					if (length < 1024
							&& commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length == 0) {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[1024];
					} else {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[2 * length];
					}
				}
				dis.readFully(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length);
				strReturn = new String(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON) {

				try {

					int length = 0;

					this.Product_id = readInteger(dis);

					this.Product_name = readString(dis);

					this.Sales_Rating = readInteger(dis);

					this.Total_expenditure = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Product_id, dos);

				// String

				writeString(this.Product_name, dos);

				// Integer

				writeInteger(this.Sales_Rating, dos);

				// Integer

				writeInteger(this.Total_expenditure, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_id=" + String.valueOf(Product_id));
			sb.append(",Product_name=" + Product_name);
			sb.append(",Sales_Rating=" + String.valueOf(Sales_Rating));
			sb.append(",Total_expenditure=" + String.valueOf(Total_expenditure));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_id == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_id);
			}

			sb.append("|");

			if (Product_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_name);
			}

			sb.append("|");

			if (Sales_Rating == null) {
				sb.append("<null>");
			} else {
				sb.append(Sales_Rating);
			}

			sb.append("|");

			if (Total_expenditure == null) {
				sb.append("<null>");
			} else {
				sb.append(Total_expenditure);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(Products_rejectStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements
			routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];
		static byte[] commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];

		public Integer Product_id;

		public Integer getProduct_id() {
			return this.Product_id;
		}

		public String Product_name;

		public String getProduct_name() {
			return this.Product_name;
		}

		public Integer Sales_Rating;

		public Integer getSales_Rating() {
			return this.Sales_Rating;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos)
				throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length) {
					if (length < 1024
							&& commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length == 0) {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[1024];
					} else {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[2 * length];
					}
				}
				dis.readFully(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length);
				strReturn = new String(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON) {

				try {

					int length = 0;

					this.Product_id = readInteger(dis);

					this.Product_name = readString(dis);

					this.Sales_Rating = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Product_id, dos);

				// String

				writeString(this.Product_name, dos);

				// Integer

				writeInteger(this.Sales_Rating, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_id=" + String.valueOf(Product_id));
			sb.append(",Product_name=" + Product_name);
			sb.append(",Sales_Rating=" + String.valueOf(Sales_Rating));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_id == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_id);
			}

			sb.append("|");

			if (Product_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_name);
			}

			sb.append("|");

			if (Sales_Rating == null) {
				sb.append("<null>");
			} else {
				sb.append(Sales_Rating);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class after_tFileInputDelimited_1Struct implements
			routines.system.IPersistableRow<after_tFileInputDelimited_1Struct> {
		final static byte[] commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];
		static byte[] commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];

		public Integer Product_id;

		public Integer getProduct_id() {
			return this.Product_id;
		}

		public String Product_name;

		public String getProduct_name() {
			return this.Product_name;
		}

		public Integer Sales_Rating;

		public Integer getSales_Rating() {
			return this.Sales_Rating;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos)
				throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length) {
					if (length < 1024
							&& commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON.length == 0) {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[1024];
					} else {
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[2 * length];
					}
				}
				dis.readFully(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length);
				strReturn = new String(
						commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON, 0,
						length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON) {

				try {

					int length = 0;

					this.Product_id = readInteger(dis);

					this.Product_name = readString(dis);

					this.Sales_Rating = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Product_id, dos);

				// String

				writeString(this.Product_name, dos);

				// Integer

				writeInteger(this.Sales_Rating, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_id=" + String.valueOf(Product_id));
			sb.append(",Product_name=" + Product_name);
			sb.append(",Sales_Rating=" + String.valueOf(Sales_Rating));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_id == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_id);
			}

			sb.append("|");

			if (Product_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_name);
			}

			sb.append("|");

			if (Sales_Rating == null) {
				sb.append("<null>");
			} else {
				sb.append(Sales_Rating);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tFileInputDelimited_1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_1Process(
			final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {

			String currentMethodName = new java.lang.Exception()
					.getStackTrace()[0].getMethodName();
			boolean resumeIt = currentMethodName.equals(resumeEntryMethodName);
			if (resumeEntryMethodName == null || resumeIt || globalResumeTicket) {// start
																					// the
																					// resume
				globalResumeTicket = true;

				tFileInputDelimited_2Process(globalMap);

				row1Struct row1 = new row1Struct();
				products_outStruct products_out = new products_outStruct();
				Products_rejectStruct Products_reject = new Products_rejectStruct();

				/**
				 * [tFileOutputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_1", false);
				start_Hash.put("tFileOutputDelimited_1",
						System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("products_out"
								+ iterateId, 0, 0);

					}
				}

				int tos_count_tFileOutputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Start to work."));
				class BytesLimit65535_tFileOutputDelimited_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tFileOutputDelimited_1 = new StringBuilder();
						log4jParamters_tFileOutputDelimited_1
								.append("Parameters:");
						log4jParamters_tFileOutputDelimited_1
								.append("USESTREAM" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("FILENAME"
										+ " = "
										+ "\"/home/evald/Downloads/Talend/DemoOutput/Products_output.csv\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("ROWSEPARATOR" + " = " + "\"\\n\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("FIELDSEPARATOR" + " = " + "\";\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("APPEND"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("INCLUDEHEADER" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("COMPRESS"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("ADVANCED_SEPARATOR" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("CSV_OPTION" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("CREATE"
								+ " = " + "true");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("SPLIT"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("FLUSHONROW" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("ROW_MODE"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("ENCODING"
								+ " = " + "\"ISO-8859-15\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("DELETE_EMPTYFILE" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("FILE_EXIST_EXCEPTION" + " = " + "true");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tFileOutputDelimited_1 - "
									+ (log4jParamters_tFileOutputDelimited_1));
					}
				}

				new BytesLimit65535_tFileOutputDelimited_1().limitLog4jByte();

				String fileName_tFileOutputDelimited_1 = "";
				fileName_tFileOutputDelimited_1 = (new java.io.File(
						"/home/evald/Downloads/Talend/DemoOutput/Products_output.csv"))
						.getAbsolutePath().replace("\\", "/");
				String fullName_tFileOutputDelimited_1 = null;
				String extension_tFileOutputDelimited_1 = null;
				String directory_tFileOutputDelimited_1 = null;
				if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(0, fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
							.substring(0, fileName_tFileOutputDelimited_1
									.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(0, fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					}
					directory_tFileOutputDelimited_1 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_1 = true;
				java.io.File filetFileOutputDelimited_1 = new java.io.File(
						fileName_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME",
						fileName_tFileOutputDelimited_1);
				if (filetFileOutputDelimited_1.exists()) {
					throw new RuntimeException(
							"The particular file \""
									+ filetFileOutputDelimited_1
											.getAbsoluteFile()
									+ "\" already exist. If you want to overwrite the file, please uncheck the"
									+ " \"Throw an error if the file already exist\" option in Advanced settings.");
				}
				int nb_line_tFileOutputDelimited_1 = 0;
				int splitedFileNo_tFileOutputDelimited_1 = 0;
				int currentRow_tFileOutputDelimited_1 = 0;

				final String OUT_DELIM_tFileOutputDelimited_1 = /**
				 * Start field
				 * tFileOutputDelimited_1:FIELDSEPARATOR
				 */
				";"/** End field tFileOutputDelimited_1:FIELDSEPARATOR */
				;

				final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 = /**
				 * Start
				 * field tFileOutputDelimited_1:ROWSEPARATOR
				 */
				"\n"/** End field tFileOutputDelimited_1:ROWSEPARATOR */
				;

				// create directory only if not exists
				if (directory_tFileOutputDelimited_1 != null
						&& directory_tFileOutputDelimited_1.trim().length() != 0) {
					java.io.File dir_tFileOutputDelimited_1 = new java.io.File(
							directory_tFileOutputDelimited_1);
					if (!dir_tFileOutputDelimited_1.exists()) {
						log.info("tFileOutputDelimited_1 - Creating directory '"
								+ dir_tFileOutputDelimited_1.getCanonicalPath()
								+ "'.");
						dir_tFileOutputDelimited_1.mkdirs();
						log.info("tFileOutputDelimited_1 - The directory '"
								+ dir_tFileOutputDelimited_1.getCanonicalPath()
								+ "' has been created successfully.");
					}
				}

				// routines.system.Row
				java.io.Writer outtFileOutputDelimited_1 = null;

				java.io.File fileToDelete_tFileOutputDelimited_1 = new java.io.File(
						fileName_tFileOutputDelimited_1);
				if (fileToDelete_tFileOutputDelimited_1.exists()) {
					fileToDelete_tFileOutputDelimited_1.delete();
				}
				outtFileOutputDelimited_1 = new java.io.BufferedWriter(
						new java.io.OutputStreamWriter(
								new java.io.FileOutputStream(
										fileName_tFileOutputDelimited_1, false),
								"ISO-8859-15"));

				resourceMap.put("out_tFileOutputDelimited_1",
						outtFileOutputDelimited_1);
				resourceMap.put("nb_line_tFileOutputDelimited_1",
						nb_line_tFileOutputDelimited_1);

				/**
				 * [tFileOutputDelimited_1 begin ] stop
				 */

				/**
				 * [tFileOutputDelimited_2 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_2", false);
				start_Hash.put("tFileOutputDelimited_2",
						System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_2";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("Products_reject"
								+ iterateId, 0, 0);

					}
				}

				int tos_count_tFileOutputDelimited_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_2 - " + ("Start to work."));
				class BytesLimit65535_tFileOutputDelimited_2 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tFileOutputDelimited_2 = new StringBuilder();
						log4jParamters_tFileOutputDelimited_2
								.append("Parameters:");
						log4jParamters_tFileOutputDelimited_2
								.append("USESTREAM" + " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("FILENAME"
										+ " = "
										+ "\"/home/evald/Downloads/Talend/DemoOutput/Products_reject.csv\"");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("ROWSEPARATOR" + " = " + "\"\\n\"");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("FIELDSEPARATOR" + " = " + "\";\"");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2.append("APPEND"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("INCLUDEHEADER" + " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2.append("COMPRESS"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("ADVANCED_SEPARATOR" + " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("CSV_OPTION" + " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2.append("CREATE"
								+ " = " + "true");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2.append("SPLIT"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("FLUSHONROW" + " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2.append("ROW_MODE"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2.append("ENCODING"
								+ " = " + "\"ISO-8859-15\"");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("DELETE_EMPTYFILE" + " = " + "false");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						log4jParamters_tFileOutputDelimited_2
								.append("FILE_EXIST_EXCEPTION" + " = " + "true");
						log4jParamters_tFileOutputDelimited_2.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tFileOutputDelimited_2 - "
									+ (log4jParamters_tFileOutputDelimited_2));
					}
				}

				new BytesLimit65535_tFileOutputDelimited_2().limitLog4jByte();

				String fileName_tFileOutputDelimited_2 = "";
				fileName_tFileOutputDelimited_2 = (new java.io.File(
						"/home/evald/Downloads/Talend/DemoOutput/Products_reject.csv"))
						.getAbsolutePath().replace("\\", "/");
				String fullName_tFileOutputDelimited_2 = null;
				String extension_tFileOutputDelimited_2 = null;
				String directory_tFileOutputDelimited_2 = null;
				if ((fileName_tFileOutputDelimited_2.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_2.lastIndexOf(".") < fileName_tFileOutputDelimited_2
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2;
						extension_tFileOutputDelimited_2 = "";
					} else {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
								.substring(0, fileName_tFileOutputDelimited_2
										.lastIndexOf("."));
						extension_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
								.substring(fileName_tFileOutputDelimited_2
										.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
							.substring(0, fileName_tFileOutputDelimited_2
									.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_2.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
								.substring(0, fileName_tFileOutputDelimited_2
										.lastIndexOf("."));
						extension_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
								.substring(fileName_tFileOutputDelimited_2
										.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2;
						extension_tFileOutputDelimited_2 = "";
					}
					directory_tFileOutputDelimited_2 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_2 = true;
				java.io.File filetFileOutputDelimited_2 = new java.io.File(
						fileName_tFileOutputDelimited_2);
				globalMap.put("tFileOutputDelimited_2_FILE_NAME",
						fileName_tFileOutputDelimited_2);
				if (filetFileOutputDelimited_2.exists()) {
					throw new RuntimeException(
							"The particular file \""
									+ filetFileOutputDelimited_2
											.getAbsoluteFile()
									+ "\" already exist. If you want to overwrite the file, please uncheck the"
									+ " \"Throw an error if the file already exist\" option in Advanced settings.");
				}
				int nb_line_tFileOutputDelimited_2 = 0;
				int splitedFileNo_tFileOutputDelimited_2 = 0;
				int currentRow_tFileOutputDelimited_2 = 0;

				final String OUT_DELIM_tFileOutputDelimited_2 = /**
				 * Start field
				 * tFileOutputDelimited_2:FIELDSEPARATOR
				 */
				";"/** End field tFileOutputDelimited_2:FIELDSEPARATOR */
				;

				final String OUT_DELIM_ROWSEP_tFileOutputDelimited_2 = /**
				 * Start
				 * field tFileOutputDelimited_2:ROWSEPARATOR
				 */
				"\n"/** End field tFileOutputDelimited_2:ROWSEPARATOR */
				;

				// create directory only if not exists
				if (directory_tFileOutputDelimited_2 != null
						&& directory_tFileOutputDelimited_2.trim().length() != 0) {
					java.io.File dir_tFileOutputDelimited_2 = new java.io.File(
							directory_tFileOutputDelimited_2);
					if (!dir_tFileOutputDelimited_2.exists()) {
						log.info("tFileOutputDelimited_2 - Creating directory '"
								+ dir_tFileOutputDelimited_2.getCanonicalPath()
								+ "'.");
						dir_tFileOutputDelimited_2.mkdirs();
						log.info("tFileOutputDelimited_2 - The directory '"
								+ dir_tFileOutputDelimited_2.getCanonicalPath()
								+ "' has been created successfully.");
					}
				}

				// routines.system.Row
				java.io.Writer outtFileOutputDelimited_2 = null;

				java.io.File fileToDelete_tFileOutputDelimited_2 = new java.io.File(
						fileName_tFileOutputDelimited_2);
				if (fileToDelete_tFileOutputDelimited_2.exists()) {
					fileToDelete_tFileOutputDelimited_2.delete();
				}
				outtFileOutputDelimited_2 = new java.io.BufferedWriter(
						new java.io.OutputStreamWriter(
								new java.io.FileOutputStream(
										fileName_tFileOutputDelimited_2, false),
								"ISO-8859-15"));

				resourceMap.put("out_tFileOutputDelimited_2",
						outtFileOutputDelimited_2);
				resourceMap.put("nb_line_tFileOutputDelimited_2",
						nb_line_tFileOutputDelimited_2);

				/**
				 * [tFileOutputDelimited_2 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("row1" + iterateId, 0, 0);

					}
				}

				int tos_count_tMap_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Start to work."));
				class BytesLimit65535_tMap_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tMap_1 = new StringBuilder();
						log4jParamters_tMap_1.append("Parameters:");
						log4jParamters_tMap_1.append("LINK_STYLE" + " = "
								+ "AUTO");
						log4jParamters_tMap_1.append(" | ");
						log4jParamters_tMap_1.append("TEMPORARY_DATA_DIRECTORY"
								+ " = " + "");
						log4jParamters_tMap_1.append(" | ");
						log4jParamters_tMap_1.append("ROWS_BUFFER_SIZE" + " = "
								+ "2000000");
						log4jParamters_tMap_1.append(" | ");
						log4jParamters_tMap_1
								.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL"
										+ " = " + "true");
						log4jParamters_tMap_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tMap_1 - " + (log4jParamters_tMap_1));
					}
				}

				new BytesLimit65535_tMap_1().limitLog4jByte();

				// ###############################
				// # Lookup's keys initialization
				int count_row1_tMap_1 = 0;

				int count_row2_tMap_1 = 0;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) globalMap
						.get("tHash_Lookup_row2"));

				row2Struct row2HashKey = new row2Struct();
				row2Struct row2Default = new row2Struct();
				// ###############################

				// ###############################
				// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
				// ###############################

				// ###############################
				// # Outputs initialization
				int count_products_out_tMap_1 = 0;

				products_outStruct products_out_tmp = new products_outStruct();
				int count_Products_reject_tMap_1 = 0;

				Products_rejectStruct Products_reject_tmp = new Products_rejectStruct();
				// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1",
						System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Start to work."));
				class BytesLimit65535_tFileInputDelimited_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tFileInputDelimited_1 = new StringBuilder();
						log4jParamters_tFileInputDelimited_1
								.append("Parameters:");
						log4jParamters_tFileInputDelimited_1
								.append("FILENAME"
										+ " = "
										+ "\"/home/evald/Downloads/Talend/Join_sampleinput.csv\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("CSV_OPTION" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("ROWSEPARATOR" + " = " + "\"\\n\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("FIELDSEPARATOR" + " = " + "\",\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("HEADER"
								+ " = " + "1");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("FOOTER"
								+ " = " + "0");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("LIMIT"
								+ " = " + "");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("REMOVE_EMPTY_ROW" + " = " + "true");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("UNCOMPRESS" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("DIE_ON_ERROR" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("ADVANCED_SEPARATOR" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("RANDOM"
								+ " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("TRIMALL"
								+ " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("TRIMSELECT" + " = " + "[{TRIM="
										+ ("false") + ", SCHEMA_COLUMN="
										+ ("Product_id") + "}, {TRIM="
										+ ("false") + ", SCHEMA_COLUMN="
										+ ("Product_name") + "}, {TRIM="
										+ ("false") + ", SCHEMA_COLUMN="
										+ ("Sales_Rating") + "}]");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("CHECK_FIELDS_NUM" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("CHECK_DATE" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("ENCODING"
								+ " = " + "\"ISO-8859-15\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("SPLITRECORD" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("ENABLE_DECODE" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tFileInputDelimited_1 - "
									+ (log4jParamters_tFileInputDelimited_1));
					}
				}

				new BytesLimit65535_tFileInputDelimited_1().limitLog4jByte();

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_1 = null;
				try {

					Object filename_tFileInputDelimited_1 = "/home/evald/Downloads/Talend/Join_sampleinput.csv";
					if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_1 = 0, random_value_tFileInputDelimited_1 = -1;
						if (footer_value_tFileInputDelimited_1 > 0
								|| random_value_tFileInputDelimited_1 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_1 = new org.talend.fileprocess.FileInputDelimited(
								"/home/evald/Downloads/Talend/Join_sampleinput.csv",
								"ISO-8859-15", ",", "\n", true, 1, 0, -1, -1,
								false);
					} catch (java.lang.Exception e) {

						log.error("tFileInputDelimited_1 - " + e.getMessage());

						System.err.println(e.getMessage());

					}

					log.info("tFileInputDelimited_1 - Retrieving records from the datasource.");

					while (fid_tFileInputDelimited_1 != null
							&& fid_tFileInputDelimited_1.nextRecord()) {
						rowstate_tFileInputDelimited_1.reset();

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_1 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_1 = 0;

							temp = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row1.Product_id = ParserUtils
											.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									rowstate_tFileInputDelimited_1
											.setException(ex_tFileInputDelimited_1);
								}

							} else {

								row1.Product_id = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 1;

							row1.Product_name = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 2;

							temp = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row1.Sales_Rating = ParserUtils
											.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									rowstate_tFileInputDelimited_1
											.setException(ex_tFileInputDelimited_1);
								}

							} else {

								row1.Sales_Rating = null;

							}

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1
										.getException();
							}

						} catch (java.lang.Exception e) {
							whetherReject_tFileInputDelimited_1 = true;

							log.error("tFileInputDelimited_1 - "
									+ e.getMessage());

							System.err.println(e.getMessage());
							row1 = null;

						}

						log.debug("tFileInputDelimited_1 - Retrieving the record "
								+ fid_tFileInputDelimited_1.getRowNumber()
								+ ".");

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */
						// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							currentComponent = "tMap_1";

							// row1
							// row1

							if (execStat) {
								runStat.updateStatOnConnection("row1"
										+ iterateId, 1, 1);
							}

							if (log.isTraceEnabled()) {
								log.trace("row1 - "
										+ (row1 == null ? "" : row1
												.toLogString()));
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_1 = false;
							boolean mainRowRejected_tMap_1 = false;

							// /////////////////////////////////////////////
							// Starting Lookup Table "row2"
							// /////////////////////////////////////////////

							boolean forceLooprow2 = false;

							row2Struct row2ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_1 = false;

								row2HashKey.Product_id = row1.Product_id;

								row2HashKey.hashCodeDirty = true;

								tHash_Lookup_row2.lookup(row2HashKey);

								if (!tHash_Lookup_row2.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_1 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row2 != null
									&& tHash_Lookup_row2.getCount(row2HashKey) > 1) { // G
																						// 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row2' and it contains more one result from keys :  row2.Product_id = '"
								// + row2HashKey.Product_id + "'");
							} // G 071

							row2Struct row2 = null;

							row2Struct fromLookup_row2 = null;
							row2 = row2Default;

							if (tHash_Lookup_row2 != null
									&& tHash_Lookup_row2.hasNext()) { // G 099

								fromLookup_row2 = tHash_Lookup_row2.next();

							} // G 099

							if (fromLookup_row2 != null) {
								row2 = fromLookup_row2;
							}

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
								// ###############################
								// # Output tables

								products_out = null;
								Products_reject = null;

								if (!rejectedInnerJoin_tMap_1) {

									// # Output table : 'products_out'
									count_products_out_tMap_1++;

									products_out_tmp.Product_id = row1.Product_id;
									products_out_tmp.Product_name = StringHandling
											.EREPLACE(row1.Product_name, "-",
													" ");
									products_out_tmp.Sales_Rating = row1.Sales_Rating;
									products_out_tmp.Total_expenditure = row2.Total_expenditure;
									products_out = products_out_tmp;
									log.debug("tMap_1 - Outputting the record "
											+ count_products_out_tMap_1
											+ " of the output table 'products_out'.");

								} // closing inner join bracket (1)
									// ###### START REJECTS #####

								// # Output reject table : 'Products_reject'
								// # Filter conditions
								if (rejectedInnerJoin_tMap_1) {
									count_Products_reject_tMap_1++;

									Products_reject_tmp.Product_id = row1.Product_id;
									Products_reject_tmp.Product_name = row1.Product_name;
									Products_reject_tmp.Sales_Rating = row1.Sales_Rating;
									Products_reject_tmp.Total_expenditure = row2.Total_expenditure;
									Products_reject = Products_reject_tmp;
									log.debug("tMap_1 - Outputting the record "
											+ count_Products_reject_tMap_1
											+ " of the output table 'Products_reject'.");

								} // closing filter/reject
									// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_1 = false;

							tos_count_tMap_1++;

							/**
							 * [tMap_1 main ] stop
							 */
							// Start of branch "products_out"
							if (products_out != null) {

								/**
								 * [tFileOutputDelimited_1 main ] start
								 */

								currentComponent = "tFileOutputDelimited_1";

								// products_out
								// products_out

								if (execStat) {
									runStat.updateStatOnConnection(
											"products_out" + iterateId, 1, 1);
								}

								if (log.isTraceEnabled()) {
									log.trace("products_out - "
											+ (products_out == null ? ""
													: products_out
															.toLogString()));
								}

								StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
								if (products_out.Product_id != null) {
									sb_tFileOutputDelimited_1
											.append(products_out.Product_id);
								}
								sb_tFileOutputDelimited_1
										.append(OUT_DELIM_tFileOutputDelimited_1);
								if (products_out.Product_name != null) {
									sb_tFileOutputDelimited_1
											.append(products_out.Product_name);
								}
								sb_tFileOutputDelimited_1
										.append(OUT_DELIM_tFileOutputDelimited_1);
								if (products_out.Sales_Rating != null) {
									sb_tFileOutputDelimited_1
											.append(products_out.Sales_Rating);
								}
								sb_tFileOutputDelimited_1
										.append(OUT_DELIM_tFileOutputDelimited_1);
								if (products_out.Total_expenditure != null) {
									sb_tFileOutputDelimited_1
											.append(products_out.Total_expenditure);
								}
								sb_tFileOutputDelimited_1
										.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);

								nb_line_tFileOutputDelimited_1++;
								resourceMap.put(
										"nb_line_tFileOutputDelimited_1",
										nb_line_tFileOutputDelimited_1);

								outtFileOutputDelimited_1
										.write(sb_tFileOutputDelimited_1
												.toString());
								log.debug("tFileOutputDelimited_1 - Writing the record "
										+ nb_line_tFileOutputDelimited_1 + ".");

								tos_count_tFileOutputDelimited_1++;

								/**
								 * [tFileOutputDelimited_1 main ] stop
								 */

							} // End of branch "products_out"

							// Start of branch "Products_reject"
							if (Products_reject != null) {

								/**
								 * [tFileOutputDelimited_2 main ] start
								 */

								currentComponent = "tFileOutputDelimited_2";

								// Products_reject
								// Products_reject

								if (execStat) {
									runStat.updateStatOnConnection(
											"Products_reject" + iterateId, 1, 1);
								}

								if (log.isTraceEnabled()) {
									log.trace("Products_reject - "
											+ (Products_reject == null ? ""
													: Products_reject
															.toLogString()));
								}

								StringBuilder sb_tFileOutputDelimited_2 = new StringBuilder();
								if (Products_reject.Product_id != null) {
									sb_tFileOutputDelimited_2
											.append(Products_reject.Product_id);
								}
								sb_tFileOutputDelimited_2
										.append(OUT_DELIM_tFileOutputDelimited_2);
								if (Products_reject.Product_name != null) {
									sb_tFileOutputDelimited_2
											.append(Products_reject.Product_name);
								}
								sb_tFileOutputDelimited_2
										.append(OUT_DELIM_tFileOutputDelimited_2);
								if (Products_reject.Sales_Rating != null) {
									sb_tFileOutputDelimited_2
											.append(Products_reject.Sales_Rating);
								}
								sb_tFileOutputDelimited_2
										.append(OUT_DELIM_tFileOutputDelimited_2);
								if (Products_reject.Total_expenditure != null) {
									sb_tFileOutputDelimited_2
											.append(Products_reject.Total_expenditure);
								}
								sb_tFileOutputDelimited_2
										.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_2);

								nb_line_tFileOutputDelimited_2++;
								resourceMap.put(
										"nb_line_tFileOutputDelimited_2",
										nb_line_tFileOutputDelimited_2);

								outtFileOutputDelimited_2
										.write(sb_tFileOutputDelimited_2
												.toString());
								log.debug("tFileOutputDelimited_2 - Writing the record "
										+ nb_line_tFileOutputDelimited_2 + ".");

								tos_count_tFileOutputDelimited_2++;

								/**
								 * [tFileOutputDelimited_2 main ] stop
								 */

							} // End of branch "Products_reject"

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

					}
				} finally {
					if (!((Object) ("/home/evald/Downloads/Talend/Join_sampleinput.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_1 != null) {
							fid_tFileInputDelimited_1.close();
						}
					}
					if (fid_tFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE",
								fid_tFileInputDelimited_1.getRowNumber());

						log.info("tFileInputDelimited_1 - Retrieved records count: "
								+ fid_tFileInputDelimited_1.getRowNumber()
								+ ".");

					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1",
						System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

				// ###############################
				// # Lookup hashes releasing
				if (tHash_Lookup_row2 != null) {
					tHash_Lookup_row2.endGet();
				}
				globalMap.remove("tHash_Lookup_row2");

				// ###############################
				log.debug("tMap_1 - Written records count in the table 'products_out': "
						+ count_products_out_tMap_1 + ".");
				log.debug("tMap_1 - Written records count in the table 'Products_reject': "
						+ count_Products_reject_tMap_1 + ".");

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("row1" + iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Done."));

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 end ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (outtFileOutputDelimited_1 != null) {
					outtFileOutputDelimited_1.flush();
					outtFileOutputDelimited_1.close();
				}

				globalMap.put("tFileOutputDelimited_1_NB_LINE",
						nb_line_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME",
						fileName_tFileOutputDelimited_1);

				resourceMap.put("finish_tFileOutputDelimited_1", true);

				log.debug("tFileOutputDelimited_1 - Written records count: "
						+ nb_line_tFileOutputDelimited_1 + " .");

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("products_out"
								+ iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileOutputDelimited_1", true);
				end_Hash.put("tFileOutputDelimited_1",
						System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_1 end ] stop
				 */

				/**
				 * [tFileOutputDelimited_2 end ] start
				 */

				currentComponent = "tFileOutputDelimited_2";

				if (outtFileOutputDelimited_2 != null) {
					outtFileOutputDelimited_2.flush();
					outtFileOutputDelimited_2.close();
				}

				globalMap.put("tFileOutputDelimited_2_NB_LINE",
						nb_line_tFileOutputDelimited_2);
				globalMap.put("tFileOutputDelimited_2_FILE_NAME",
						fileName_tFileOutputDelimited_2);

				resourceMap.put("finish_tFileOutputDelimited_2", true);

				log.debug("tFileOutputDelimited_2 - Written records count: "
						+ nb_line_tFileOutputDelimited_2 + " .");

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("Products_reject"
								+ iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_2 - " + ("Done."));

				ok_Hash.put("tFileOutputDelimited_2", true);
				end_Hash.put("tFileOutputDelimited_2",
						System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_2 end ] stop
				 */

			}// end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent,
					globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row2");

			try {

				/**
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (resourceMap.get("finish_tFileOutputDelimited_1") == null) {

					java.io.Writer outtFileOutputDelimited_1 = (java.io.Writer) resourceMap
							.get("out_tFileOutputDelimited_1");
					if (outtFileOutputDelimited_1 != null) {
						outtFileOutputDelimited_1.flush();
						outtFileOutputDelimited_1.close();
					}

				}

				/**
				 * [tFileOutputDelimited_1 finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_2 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_2";

				if (resourceMap.get("finish_tFileOutputDelimited_2") == null) {

					java.io.Writer outtFileOutputDelimited_2 = (java.io.Writer) resourceMap
							.get("out_tFileOutputDelimited_2");
					if (outtFileOutputDelimited_2 != null) {
						outtFileOutputDelimited_2.flush();
						outtFileOutputDelimited_2.close();
					}

				}

				/**
				 * [tFileOutputDelimited_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public static class row2Struct implements
			routines.system.IPersistableComparableLookupRow<row2Struct> {
		final static byte[] commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];
		static byte[] commonByteArray_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer Product_id;

		public Integer getProduct_id() {
			return this.Product_id;
		}

		public Integer Total_expenditure;

		public Integer getTotal_expenditure() {
			return this.Total_expenditure;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime
						* result
						+ ((this.Product_id == null) ? 0 : this.Product_id
								.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row2Struct other = (row2Struct) obj;

			if (this.Product_id == null) {
				if (other.Product_id != null)
					return false;

			} else if (!this.Product_id.equals(other.Product_id))

				return false;

			return true;
		}

		public void copyDataTo(row2Struct other) {

			other.Product_id = this.Product_id;
			other.Total_expenditure = this.Total_expenditure;

		}

		public void copyKeysDataTo(row2Struct other) {

			other.Product_id = this.Product_id;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos)
				throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos,
				ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_ETL_DEMO_EDUREKA_J_1_ETL_HANDS_ON) {

				try {

					int length = 0;

					this.Product_id = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Product_id, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.Total_expenditure = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeInteger(this.Total_expenditure, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Product_id=" + String.valueOf(Product_id));
			sb.append(",Total_expenditure=" + String.valueOf(Total_expenditure));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Product_id == null) {
				sb.append("<null>");
			} else {
				sb.append(Product_id);
			}

			sb.append("|");

			if (Total_expenditure == null) {
				sb.append("<null>");
			} else {
				sb.append(Total_expenditure);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Product_id,
					other.Product_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_2Process(
			final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {

			String currentMethodName = new java.lang.Exception()
					.getStackTrace()[0].getMethodName();
			boolean resumeIt = currentMethodName.equals(resumeEntryMethodName);
			if (resumeEntryMethodName == null || resumeIt || globalResumeTicket) {// start
																					// the
																					// resume
				globalResumeTicket = true;

				row2Struct row2 = new row2Struct();

				/**
				 * [tAdvancedHash_row2 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row2", false);
				start_Hash
						.put("tAdvancedHash_row2", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row2";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("row2" + iterateId, 0, 0);

					}
				}

				int tos_count_tAdvancedHash_row2 = 0;

				class BytesLimit65535_tAdvancedHash_row2 {
					public void limitLog4jByte() throws Exception {

					}
				}

				new BytesLimit65535_tAdvancedHash_row2().limitLog4jByte();

				// connection name:row2
				// source node:tFileInputDelimited_2 -
				// inputs:(after_tFileInputDelimited_1) outputs:(row2,row2) |
				// target node:tAdvancedHash_row2 - inputs:(row2) outputs:()
				// linked node: tMap_1 - inputs:(row1,row2)
				// outputs:(products_out,Products_reject)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row2Struct> getLookup(matchingModeEnum_row2);

				globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);

				/**
				 * [tAdvancedHash_row2 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_2 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_2", false);
				start_Hash.put("tFileInputDelimited_2",
						System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_2";

				int tos_count_tFileInputDelimited_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_2 - " + ("Start to work."));
				class BytesLimit65535_tFileInputDelimited_2 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tFileInputDelimited_2 = new StringBuilder();
						log4jParamters_tFileInputDelimited_2
								.append("Parameters:");
						log4jParamters_tFileInputDelimited_2
								.append("FILENAME"
										+ " = "
										+ "\"/home/evald/Downloads/Talend/Join_Lookup.csv\"");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("CSV_OPTION" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("ROWSEPARATOR" + " = " + "\"\\n\"");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("FIELDSEPARATOR" + " = " + "\",\"");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2.append("HEADER"
								+ " = " + "1");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2.append("FOOTER"
								+ " = " + "0");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2.append("LIMIT"
								+ " = " + "");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("REMOVE_EMPTY_ROW" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("UNCOMPRESS" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("DIE_ON_ERROR" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("ADVANCED_SEPARATOR" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2.append("RANDOM"
								+ " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2.append("TRIMALL"
								+ " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("TRIMSELECT" + " = " + "[{TRIM="
										+ ("false") + ", SCHEMA_COLUMN="
										+ ("Product_id") + "}, {TRIM="
										+ ("false") + ", SCHEMA_COLUMN="
										+ ("Total_expenditure") + "}]");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("CHECK_FIELDS_NUM" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("CHECK_DATE" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2.append("ENCODING"
								+ " = " + "\"US-ASCII\"");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("SPLITRECORD" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						log4jParamters_tFileInputDelimited_2
								.append("ENABLE_DECODE" + " = " + "false");
						log4jParamters_tFileInputDelimited_2.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tFileInputDelimited_2 - "
									+ (log4jParamters_tFileInputDelimited_2));
					}
				}

				new BytesLimit65535_tFileInputDelimited_2().limitLog4jByte();

				final routines.system.RowState rowstate_tFileInputDelimited_2 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_2 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_2 = null;
				try {

					Object filename_tFileInputDelimited_2 = "/home/evald/Downloads/Talend/Join_Lookup.csv";
					if (filename_tFileInputDelimited_2 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_2 = 0, random_value_tFileInputDelimited_2 = -1;
						if (footer_value_tFileInputDelimited_2 > 0
								|| random_value_tFileInputDelimited_2 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_2 = new org.talend.fileprocess.FileInputDelimited(
								"/home/evald/Downloads/Talend/Join_Lookup.csv",
								"US-ASCII", ",", "\n", false, 1, 0, -1, -1,
								false);
					} catch (java.lang.Exception e) {

						log.error("tFileInputDelimited_2 - " + e.getMessage());

						System.err.println(e.getMessage());

					}

					log.info("tFileInputDelimited_2 - Retrieving records from the datasource.");

					while (fid_tFileInputDelimited_2 != null
							&& fid_tFileInputDelimited_2.nextRecord()) {
						rowstate_tFileInputDelimited_2.reset();

						row2 = null;

						row2 = null;

						boolean whetherReject_tFileInputDelimited_2 = false;
						row2 = new row2Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_2 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_2 = 0;

							temp = fid_tFileInputDelimited_2
									.get(columnIndexWithD_tFileInputDelimited_2);
							if (temp.length() > 0) {

								try {

									row2.Product_id = ParserUtils
											.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_2) {
									rowstate_tFileInputDelimited_2
											.setException(ex_tFileInputDelimited_2);
								}

							} else {

								row2.Product_id = null;

							}

							columnIndexWithD_tFileInputDelimited_2 = 1;

							temp = fid_tFileInputDelimited_2
									.get(columnIndexWithD_tFileInputDelimited_2);
							if (temp.length() > 0) {

								try {

									row2.Total_expenditure = ParserUtils
											.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_2) {
									rowstate_tFileInputDelimited_2
											.setException(ex_tFileInputDelimited_2);
								}

							} else {

								row2.Total_expenditure = null;

							}

							if (rowstate_tFileInputDelimited_2.getException() != null) {
								throw rowstate_tFileInputDelimited_2
										.getException();
							}

						} catch (java.lang.Exception e) {
							whetherReject_tFileInputDelimited_2 = true;

							log.error("tFileInputDelimited_2 - "
									+ e.getMessage());

							System.err.println(e.getMessage());
							row2 = null;

						}

						log.debug("tFileInputDelimited_2 - Retrieving the record "
								+ fid_tFileInputDelimited_2.getRowNumber()
								+ ".");

						/**
						 * [tFileInputDelimited_2 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_2 main ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						tos_count_tFileInputDelimited_2++;

						/**
						 * [tFileInputDelimited_2 main ] stop
						 */
						// Start of branch "row2"
						if (row2 != null) {

							/**
							 * [tAdvancedHash_row2 main ] start
							 */

							currentComponent = "tAdvancedHash_row2";

							// row2
							// row2

							if (execStat) {
								runStat.updateStatOnConnection("row2"
										+ iterateId, 1, 1);
							}

							if (log.isTraceEnabled()) {
								log.trace("row2 - "
										+ (row2 == null ? "" : row2
												.toLogString()));
							}

							row2Struct row2_HashRow = new row2Struct();

							row2_HashRow.Product_id = row2.Product_id;

							row2_HashRow.Total_expenditure = row2.Total_expenditure;

							tHash_Lookup_row2.put(row2_HashRow);

							tos_count_tAdvancedHash_row2++;

							/**
							 * [tAdvancedHash_row2 main ] stop
							 */

						} // End of branch "row2"

						/**
						 * [tFileInputDelimited_2 end ] start
						 */

						currentComponent = "tFileInputDelimited_2";

					}
				} finally {
					if (!((Object) ("/home/evald/Downloads/Talend/Join_Lookup.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_2 != null) {
							fid_tFileInputDelimited_2.close();
						}
					}
					if (fid_tFileInputDelimited_2 != null) {
						globalMap.put("tFileInputDelimited_2_NB_LINE",
								fid_tFileInputDelimited_2.getRowNumber());

						log.info("tFileInputDelimited_2 - Retrieved records count: "
								+ fid_tFileInputDelimited_2.getRowNumber()
								+ ".");

					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_2 - " + ("Done."));

				ok_Hash.put("tFileInputDelimited_2", true);
				end_Hash.put("tFileInputDelimited_2",
						System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_2 end ] stop
				 */

				/**
				 * [tAdvancedHash_row2 end ] start
				 */

				currentComponent = "tAdvancedHash_row2";

				tHash_Lookup_row2.endPut();

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("row2" + iterateId, 2, 0);
					}
				}

				ok_Hash.put("tAdvancedHash_row2", true);
				end_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row2 end ] stop
				 */

			}// end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent,
					globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_2 finally ] start
				 */

				currentComponent = "tFileInputDelimited_2";

				/**
				 * [tFileInputDelimited_2 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row2 finally ] start
				 */

				currentComponent = "tAdvancedHash_row2";

				/**
				 * [tAdvancedHash_row2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	private PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final J_1_ETL_HANDS_ON J_1_ETL_HANDS_ONClass = new J_1_ETL_HANDS_ON();

		int exitCode = J_1_ETL_HANDS_ONClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'J_1_ETL_HANDS_ON' - Done.");
		}

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}

		if (!"".equals(log4jLevel)) {
			if ("trace".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.OFF);
			}
			org.apache.log4j.Logger.getRootLogger().setLevel(log.getLevel());
		}
		log.info("TalendJob: 'J_1_ETL_HANDS_ON' - Start.");

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket
				// can't open
				System.err.println("The statistics socket port " + portStats
						+ " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}

		try {
			// call job/subjob with an existing context, like:
			// --context=production. if without this parameter, there will use
			// the default context instead.
			java.io.InputStream inContext = J_1_ETL_HANDS_ON.class
					.getClassLoader().getResourceAsStream(
							"etl_demo_edureka/j_1_etl_hands_on_0_1/contexts/"
									+ contextStr + ".properties");
			if (isDefaultContext && inContext == null) {

			} else {
				if (inContext != null) {
					// defaultProps is in order to keep the original context
					// value
					defaultProps.load(inContext);
					inContext.close();
					context = new ContextProperties(defaultProps);
				} else {
					// print info and job continue to run, for case:
					// context_param is not empty.
					System.err.println("Could not find the context "
							+ contextStr);
				}
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param
							.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil
				.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName,
				jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName,
				parent_part_launcher, Thread.currentThread().getId() + "", "",
				"", "", "",
				resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory)
					+ " bytes memory increase when running : J_1_ETL_HANDS_ON");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;
		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher,
				Thread.currentThread().getId() + "", "", "" + returnCode, "",
				"", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index),
							keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index),
							keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		}

	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" },
			{ "\\'", "\'" }, { "\\r", "\r" }, { "\\f", "\f" }, { "\\b", "\b" },
			{ "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex,
							index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left
			// into the result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 106472 characters generated by Talend Data Integration on the September 11,
 * 2019 2:03:20 AM CEST
 ************************************************************************************************/
