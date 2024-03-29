package org.designpatterns.example.behavior.mediator;

/**
 * @author cbf4Life cbf4life@126.com
 * I'm glad to share my knowledge with you all.
 * 同事类
 */
public abstract class Colleague {
    protected Mediator mediator;

    public Colleague(Mediator _mediator) {
        mediator = _mediator;
    }
}
