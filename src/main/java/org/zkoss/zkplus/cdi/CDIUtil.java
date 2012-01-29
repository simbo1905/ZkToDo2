package org.zkoss.zkplus.cdi;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.zkoss.xel.XelException;
public class CDIUtil
{
	private static BeanManager _manager;
	public static BeanManager getBeanManager()
	{
		if(_manager != null)
			return _manager;
		try
		{
			final InitialContext initialContext = new InitialContext();
			_manager = (BeanManager)initialContext.lookup("java:comp/env/BeanManager");
		}
		catch(NamingException e)
		{
			try
			{
				final InitialContext initialContext = new InitialContext();
				_manager = (BeanManager)initialContext.lookup("java:comp/BeanManager");
			}
			catch(NamingException e1)
			{
				throw XelException.Aide.wrap(e1, "Cannot locate the BeanManager for JavaEE 6 using "+CDIUtil.class.getSimpleName());
			}
		}
		return _manager;
	}
}
