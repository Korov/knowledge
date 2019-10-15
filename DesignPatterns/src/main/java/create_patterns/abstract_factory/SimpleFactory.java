package create_patterns.abstract_factory;

public class SimpleFactory extends Factory {
    @Override
    public Sample creator(int which) {
        if (which == 1) {
            return new SampleA();
        } else {
            return new SampleB();
        }
    }

    @Override
    public Sample2 creator(String name) {
        if (name.equals("1")) {
            return new Sample2A();
        } else {
            return new Sample2B();
        }
    }
}
