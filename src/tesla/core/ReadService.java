package tesla.core;

import java.io.Serializable;
import java.lang.reflect.Method;

import tesla.exception.TeslaException;
import tesla.persistence.Persistence;
import tesla.persistence.Persistent;
import tesla.store.Request;
import tesla.store.ServerBean;

public abstract class ReadService<ReturnType extends Serializable, PersistentType extends Persistent> extends Service<ReturnType> {

	private Class<PersistentType> classPersistentType = null;
	protected abstract ReturnType execute(Request request, PersistentType persistentType) throws TeslaException;
	public ReadService(ServerBean serverBean) {
		super(serverBean);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected final ReturnType execute(Request request) {
		try {
			return execute(request, (PersistentType) Persistence.read(getPersistentType()));
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Class<PersistentType> getPersistentType() {
		if(classPersistentType==null) {
			Method[] methods = this.getClass().getDeclaredMethods();
			for(Method method : methods) {
				if(method.getName().equals("execute") && method.getParameterTypes().length==2 && method.getParameterTypes()[1]!=Persistent.class) {
					classPersistentType = (Class<PersistentType>) method.getParameterTypes()[1];
					System.out.println("Initializing " + ReadService.class.getSimpleName() + " " + this.getClass().getName()+"<"+method.getReturnType().getName()+", "+classPersistentType.getName()+">");
				}
			}
		}
		return classPersistentType;
	}
}