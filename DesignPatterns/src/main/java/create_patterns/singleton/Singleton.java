package create_patterns.singleton;

public class Singleton {
    private static Singleton singleton = null;

    public static synchronized Singleton getSingleton() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}
