/**
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
*/
package org.zkforge.zktodo2.binding;

import java.lang.reflect.Method;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 * CommandBinder bind events such as "onClick"/"onChange" onto a POJO business object.  
 * 
 * An optional converter class can be specified. The converter bridges the View world of the 
 * onX Event into the POJO business world. This is because we don't want the POJO business 
 * object to be compiled against the ZK view classes. 
 * 
 * See the presentation at {@link http://dl.dropbox.com/u/2622674/zk/zk-mvvm.pdf} 
 * for more information about the Model-View-Binder (MVB) pattern (also know as the 
 * Model-View-View-Model (MVVM) pattern. 

 * @see org.zkforge.zktodo2.binding.CommandBinderInit
 * @see org.zkforge.zktodo2.binding.CommandConverter
 * @author simon
 */
public class CommandBinder {

	public CommandBinder(Component comp, String eventName, final Object target, String methodName, final CommandConverter converter) {
		Method[] methods = target.getClass().getDeclaredMethods();
		for (final Method m : methods) {
			if (m.getName().equals(methodName)) {
				comp.addEventListener(eventName, new EventListener() {
					public void onEvent(Event event) throws Exception {
						Object[] parameters = new Object[]{}; // default is void method signature
						if( converter != null ){
							parameters = converter.coerceToModelParameters(event, m);
						}
						try {
							m.invoke(target, parameters);
						} catch (Exception e) {
							final String message = new StringBuilder()
								.append(this.getClass().getName())
								.append(" exception on reflective invokation of ")
								.append(target.getClass().getCanonicalName())
								.append(".")
								.append(m.getName())
								.append(" using parameters ")
								.append(format(parameters))
								.toString();
							System.err.println(message);
						}
					}

					private String format(Object[] parameters) {
						StringBuilder builder = new StringBuilder("{");
						for( Object o : parameters ){
							builder.append(" ")
								.append(o.getClass().getName())
								.append(":")
								.append(o)
								.append(",");
						}
						String s = builder.toString().substring(0, builder.toString().length() -1);
						return s+" }";
					}

				});
			}
		}
	}
}
