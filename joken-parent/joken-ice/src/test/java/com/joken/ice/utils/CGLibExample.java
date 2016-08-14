/*
 * package com.joken.ice.utils;
 * 
 * import java.lang.reflect.Method; import java.text.SimpleDateFormat; import
 * java.util.Date;
 * 
 * import org.springframework.asm.Type; import
 * org.springframework.cglib.core.Signature; import
 * org.springframework.cglib.proxy.Enhancer; import
 * org.springframework.cglib.proxy.InterfaceMaker; import
 * org.springframework.cglib.proxy.MethodInterceptor; import
 * org.springframework.cglib.proxy.MethodProxy;
 * 
 * public class CGLibExample {
 * 
 * @SuppressWarnings("unchecked") public static void main(String[] args) {
 * 
 * // 定义一个参数是字符串类型的setCreatedAt方法 InterfaceMaker im = new InterfaceMaker();
 * im.add(new Signature("setCreatedAt", Type.VOID_TYPE, new Type[] { Type
 * .getType(String.class) }), null); // new Signature("getKey",Type.OBJECT , new
 * // Type[]{Type.getType(Object.class)}); String name = "getKey"; Type
 * returnType = Type.DOUBLE_TYPE; Type[] argumentTypes = new Type[] {
 * Type.getType(Double.class) }; Signature signature = new Signature(name,
 * returnType, argumentTypes); im.add(signature, null); Class myInterface =
 * im.create();
 * 
 * Enhancer enhancer = new Enhancer();
 * enhancer.setSuperclass(ExampleBean.class); enhancer.setInterfaces(new Class[]
 * { myInterface }); enhancer.setCallback(new MethodInterceptor() { public
 * Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
 * throws Throwable {
 * 
 * ExampleBean bean = (ExampleBean) obj;
 * 
 * // 调用字符串类型的setCreatedAt方法时，转换成Date型后调用Setter if
 * (method.getName().startsWith("setCreatedAt") && args[0] != null && args[0]
 * instanceof String) {
 * 
 * SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"); Date
 * date = null; try { date = sdf.parse((String) args[0]); } catch (final
 * Exception e) { nop } bean.setCreatedAt(date); return null;
 * 
 * } return proxy.invokeSuper(obj, args); } });
 * 
 * // 生成一个Bean ExampleBean bean = (ExampleBean) enhancer.create();
 * bean.setId(999);
 * 
 * try { Method method = bean.getClass().getMethod("setCreatedAt", new Class[] {
 * String.class }); method.invoke(bean, new Object[] { "2010-05-31 10:10:10" });
 * } catch (final Exception e) { e.printStackTrace(); }
 * 
 * System.out.printf("id : [%d] createdAt : [%s]\n", bean.getId(),
 * bean.getCreatedAt()); } }
 */