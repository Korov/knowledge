package create_patterns.abstract_factory;

import java.lang.reflect.InvocationTargetException;

public class BombFactory extends Factory {
    @Override
    public Sample creator(int which) {
        System.out.println("BombFactory");
        if (which == 1) {
            return new SampleA();
        } else {
            return new SampleB();
        }
    }

    @Override
    public Sample2 creator(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("BombFactory");
        return (Sample2) Class.forName(name).getDeclaredConstructor(Sample2.class).newInstance();
    }
}
