/**
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
*/
package org.zkforge.zktodo2.binding;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.sys.ComponentCtrl;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zkplus.databind.DataBinder;

/**
 * This class resolves annotations such as onClick="@{targetBean}" and creates a 
 * CommandBinder which maps a method on the target bean to an event handler on the 
 * annotated component. The "id" of the component is used as the method name to invoke
 * on the target bean. 
 * 
 * There can be an optional converter such as 
 * 
 * <datebox id="date" 
 * 	 onChange="@{toDoViewModel, converter='org.zkforge.zktodo2.binding.InputEventCommandConverter'}"/>
 * 
 * If a converter is specified then the method coerceToModelParameters will be called
 * which can resolve the business objects to pass into the method of the target bean. 
 * 
 * AnnotateDataBinderInit and AnnotateDataBinder have private rather than protected
 * methods so are not extensible. This class therefore has some cut and paste from
 * those classes.  
 * 
 * @author simon
 *
 */
public class CommandBinderInit extends DataBinder implements Initiator, InitiatorExt {

	private static final long serialVersionUID = 1L;
	
	public boolean doCatch(Throwable t) throws Exception {
		return false;
	}

	public void doFinally() throws Exception {
		// nothing to do		
	}

	public void doAfterCompose(Page p) throws Exception {
		// nothing to do
	}

	protected Page page = null;
	
	protected String root = null;
	
	@SuppressWarnings("unchecked")
	public void doInit(Page page, Map args) throws Exception {
		this.page = page;
		root = (String) args.get("root");
		return;
	}
	
	/**
	 * The logic to resolve the component follows the logic documented for AnnotateDataBinderInit.
	 * You can pass the name of the component to scan for annotations to speed up page loading: 
	 * 
	 * 	<?init class="org.zkforge.zktodo2.binding.CommandBinderInit" root="./buttons"?>
	 * 
	 */
	public void doAfterCompose(Page page, Component[] components) throws Exception {
		if( root == null ){
			// load for everything on the page
			for( final Component c : components ){
				loadAnnotations(c);
			}
		} else {
			if( root.startsWith("/") ) { // absolute path
				final Component comp = Path.getComponent(root);
				if( comp != null ) {
					loadAnnotations(comp);
				}
			} else if( root.startsWith("./") || root.startsWith("../") ) { // scan relative paths
				for( Component vroot : components ) {
					final Component comp = Path.getComponent(vroot.getSpaceOwner(), root);
					if( comp != null ){
						loadAnnotations(comp);
					}
				}
			}
		}
		return;
	}

	protected void loadAnnotations(Component comp) {

		loadComponentPropertyAnnotationByAnnotName(comp, "default");
		
		@SuppressWarnings("unchecked")
		final List<Component> children = comp.getChildren();
		for (final Iterator<Component> it = children.iterator(); it.hasNext(); ) {
			loadAnnotations((Component) it.next()); //recursive
		}
		return;
	}

	protected void loadComponentPropertyAnnotationByAnnotName(Component comp,
			String annotName) {
		String methodName = comp.getId();
		if (methodName == null && "".equals(methodName)) {
			return;
		}
		ComponentCtrl compCtrl = (ComponentCtrl) comp;
		
		@SuppressWarnings("unchecked")
		final List<String> props = compCtrl.getAnnotatedPropertiesBy(annotName);
		
		for (@SuppressWarnings("unchecked") 
				final Iterator it = props.iterator(); it.hasNext();) {
			
			final String eventName = (String) it.next();
			
			if (eventName.startsWith("on") ) {
				final Object[] annotationDetails = loadPropertyAnnotation(comp,
						eventName, annotName);

				String targetName = (String) annotationDetails[0];
				
				final Object target = page.getXelVariable(targetName);
								
				CommandConverter converter = null;
				
				String converterClassName = (String) annotationDetails[4];
				
				if( converterClassName != null && !"".equals(converterClassName)) {

					try {
						@SuppressWarnings("unchecked")
						Class<? extends CommandConverter> converterClass = (Class<? extends CommandConverter>) 
							Class.forName(converterClassName, true, CommandBinderInit.class.getClassLoader());
						converter = converterClass.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				new CommandBinder(comp, eventName, target, methodName, converter);
				
			}
		}
	}
}
