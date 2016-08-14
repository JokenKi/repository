/*******************************************************************************
 * Portions created by Sebastian Thomschke are copyright (c) 2005-2011 Sebastian
 * Thomschke.
 * 
 * All Rights Reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Thomschke - initial implementation.
 *******************************************************************************/
package net.sf.oval.context;

import java.lang.reflect.Method;

import net.sf.oval.Validator;
import net.sf.oval.internal.util.SerializableMethod;
import net.sf.oval.internal.util.StringUtils;

/**
 * @author Sebastian Thomschke
 */
public class MethodParameterContext extends OValContext {
	private static final long serialVersionUID = 1L;

	private final SerializableMethod method;
	private final int parameterIndex;
	private final String parameterName;

	public MethodParameterContext(final Method method,
			final int parameterIndex, final String parameterName) {
		this.method = SerializableMethod.getInstance(method);
		this.parameterIndex = parameterIndex;
		this.parameterName = parameterName == null ? "param" + parameterIndex
				: parameterName;
		this.compileTimeType = method.getParameterTypes()[parameterIndex];
	}

	/**
	 * @return Returns the method.
	 */
	public Method getMethod() {
		return method.getMethod();
	}

	/**
	 * @return Returns the parameterIndex.
	 */
	public int getParameterIndex() {
		return parameterIndex;
	}

	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer key = new StringBuffer();
		key.append(method.getDeclaringClass().getCanonicalName());
		key.append(".").append(method.getName()).append(".arg")
				.append(parameterIndex);
		String paramName = Validator.getValidationFieldResolver().getMessage(
				key.toString());
		if (paramName != null && !paramName.isEmpty()) {
			return Validator.getMessageResolver().getMessage(
					"net.sf.oval.context.MethodParameterContext.parameter")
					+ "(" + paramName + ")";
		}
		String[] paramClazz = new String[method.getParameterTypes().length];
		for (int i = 0, len = paramClazz.length; i < len; i++) {
			paramClazz[i] = method.getParameterTypes()[i].getSimpleName()
					+ " arg" + i;
		}
		return method.getDeclaringClass().getSimpleName()
				+ "."
				+ method.getName()
				+ "("
				+ StringUtils.implode(paramClazz, ",")
				+ ") "
				+ Validator.getMessageResolver().getMessage(
						"net.sf.oval.context.MethodParameterContext.parameter")
				+ " "
				+ parameterIndex
				+ (parameterName == null || parameterName.length() == 0 ? ""
						: " (" + parameterName + ")");
	}
}
