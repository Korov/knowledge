package org.designpatterns.example.creational.prototype;

import java.io.Serializable;

public class TestItem implements Serializable {
    private static final long serialVersionUID = -5315772464206924016L;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
}
