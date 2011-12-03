/**
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
*/
package org.zkforge.zktodo2.binding;

import java.lang.reflect.Method;

import org.zkoss.zk.ui.event.Event;

/**
 * CommandConverter can be used to bridge the intentional divide between 
 * the POJO business object (i.e. ViewModel) and the ZK framework. The 
 * event can be used to resolve business state held within the ZK desktop 
 * (i.e. the View) which is returned as an array list of arguments which 
 * will be invoked on the specified method of the ViewModel. 
 * 
 * @author simon
 */
public interface CommandConverter {
	Object[] coerceToModelParameters( Event e, Method m );
}