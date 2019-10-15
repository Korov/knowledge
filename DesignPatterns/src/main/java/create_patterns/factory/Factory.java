package create_patterns.factory;

public class Factory {
    public static Sample creator(int which) {
        if (which == 1) {
            return new SampleA();
        } else {
            return new SampleB();
        }
    }
}
