package com.korov.springboot.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
public class EncoreableIntroducer {
    @DeclareParents(value="com.korov.springboot.aspect.Performance+",defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;
}
