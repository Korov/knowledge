package org.designpatterns.example.behavior.template;

public class Clinet {
    public static void main(String[] args) {
        TemplateClass template1 = new MyTemplate1();
        TemplateClass template2 = new MyTemplate2();
        template1.templateMethod();
        template2.templateMethod();
    }
}
