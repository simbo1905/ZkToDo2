
package org.zkforge.zktodo2;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateFormatConverter implements org.zkoss.zkplus.databind.TypeConverter {

	public Object coerceToBean(Object val, org.zkoss.zk.ui.Component comp) {
		//do nothing
		return val;
	}
	/**
	 * Depending whether the data is coming from the database or coming from the datebox 
	 * we might be passed either a java.util.Date or a java.sql.Timestamp
	 * 
	 * @see org.zkoss.zkplus.databind.TypeConverter#coerceToUi(java.lang.Object, org.zkoss.zk.ui.Component)
	 */
	public Object coerceToUi(Object val, org.zkoss.zk.ui.Component comp) {
		Date date = null;
		if( val instanceof Timestamp ){
			final Timestamp timestamp = (Timestamp)val;
			date = new Date(timestamp.getTime());
		} else if( val instanceof Date ){
			date = (Date)val;
		}

		//prepare dateFormat and convert Date to String
		return (new SimpleDateFormat("MM/dd/yyyy")).format(date);
	}
}