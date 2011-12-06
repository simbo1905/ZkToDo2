/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
package org.zkforge.zktodo2.binding;

import java.lang.reflect.Method;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;

public class InputEventCommandConverter implements CommandConverter, TypeConverter {

	public Object[] coerceToModelParameters(Event e, Method m) {
		Component target = e.getTarget();
		InputEvent ie = (InputEvent) e;

		Object data = null;
		if( target instanceof Intbox ){
			data = Integer.parseInt(ie.getValue());
		} else if ( target instanceof Datebox ) {
			Datebox db = (Datebox)target;
			data = db.getValue();
		} else {
			data = ie.getValue();
		}
	
		return new Object[]{data};
	}

	public Object coerceToBean(Object val, Component comp) {
		return TypeConverter.IGNORE;
	}

	public Object coerceToUi(Object val, Component comp) {
		return TypeConverter.IGNORE;
	}

}
