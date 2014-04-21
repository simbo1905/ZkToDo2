package org.zkforge.zktodo2.ui;

import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
/**
 * This class converts the java.sql type to java.util type for a datebox
 * @author simbo
 */
@SuppressWarnings("rawtypes")
public final class TimestampConverter implements Converter {
	
	public TimestampConverter(){
		//noop
	}
	
	@Override
	public Object coerceToUi(Object val, Component c, BindContext b) {
		return val;
	}

	@Override
	public Object coerceToBean(Object val, Component c, BindContext b) {
		Date date = (Date) val;
		return new java.sql.Timestamp(date.getTime());
	}
}