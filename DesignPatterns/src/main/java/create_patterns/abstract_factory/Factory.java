package create_patterns.abstract_factory;

import java.lang.reflect.InvocationTargetException;

public abstract class Factory {
    public abstract Sample creator(int which);

    public abstract Sample2 creator(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
