package net.platform.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * 功能描述： 反射工具类.提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class Reflections {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(Reflections.class);


 
    /**
     * 
     * 功能描述： 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:30:13
     *        </p>
     * @param object
     * @param fieldName
     * @return Object
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 
     * 功能描述：直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:30:25
     *        </p>
     * @param object
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            if (logger.isDebugEnabled()) {
                logger.error("设置对象属性值异常" + e.getMessage() + ", [ReflectionUtils.setFieldValue]", e);
            }
        }
    }

    /**
     * 
     * 功能描述：直接调用对象方法, 无视private/protected修饰符.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:31:00
     *        </p>
     * @param object
     * @param methodName
     * @param parameterTypes
     * @param parameters
     * @return Object
     */
    public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
            final Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }

        method.setAccessible(true);

        try {
            return method.invoke(object, parameters);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 
     * 功能描述：循环向上转型, 获取对象的DeclaredField. 如向上转型到Object仍无法找到, 返回null.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:31:11
     *        </p>
     * @param object
     * @param fieldName
     * @return
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        Assert.notNull(object, "object不能为空");
        Assert.hasText(fieldName, "fieldName");
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 
     * 功能描述：强行设置Field可访问.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:31:21
     *        </p>
     * @param field
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 
     * 功能描述：循环向上转型, 获取对象的DeclaredMethod. 如向上转型到Object仍无法找到, 返回null.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:31:28
     *        </p>
     * @param object
     * @param methodName
     * @param parameterTypes
     * @return
     */
    protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        Assert.notNull(object, "object不能为空");

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {// NOSONAR
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 
     * 功能描述：通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:31:40
     *        </p>
     * @param <T>
     * @param clazz
     * @return Class<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 
     * 功能描述：通过反射, 获得定义Class时声明的父类的泛型参数的类型. 如无法找到
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:31:50
     *        </p>
     * @param clazz
     * @param index
     * @return Class
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }


    /**
     * 
     * 功能描述：将反射时的checked exception转换为unchecked exception.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:32:42
     *        </p>
     * @param e
     * @return
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }


    /**
     * 
     * 功能描述：实例化类
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2014-7-18 下午04:33:22
     *        </p>
     * @param className
     * @param args
     * @return
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static Object newInstance(String className, Object[] args) throws ClassNotFoundException, SecurityException,
            NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException,
            InvocationTargetException {
        Class newoneClass = Class.forName(className);
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Constructor cons = newoneClass.getConstructor(argsClass);
        return cons.newInstance(args);
    }

}
