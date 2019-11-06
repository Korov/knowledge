package com.korov.springboot.util;

import java.util.List;

public class CollectionsUtil {
    public static boolean isEmpty(List collections) {
        if (null == collections || 0 == collections.size()) {
            return false;
        }
        return true;
    }
}
