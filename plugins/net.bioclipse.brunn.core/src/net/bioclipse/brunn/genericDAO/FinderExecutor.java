package net.bioclipse.brunn.genericDAO;

import java.lang.reflect.Method;
import java.util.List;

public interface FinderExecutor<T>
{
    /**
     * Execute a finder method with the appropriate arguments
     */
    List<T> executeFinder(Method method, Object[] queryArgs);

//    Iterator<T> iterateFinder(Method method, Object[] queryArgs);

//    ScrollableResults scrollFinder(Method method, Object[] queryArgs);
}
