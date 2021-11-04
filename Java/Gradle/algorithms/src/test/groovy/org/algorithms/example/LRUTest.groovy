package org.algorithms.example

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.Test

@Slf4j
class LRUTest {

    @Test
    void test() {
        LRUList list = new LRUList()
        list.cap = 5
    }

    class LRUList {
        LinkedHashMap<String, String> map = new LinkedHashMap<>()
        int cap

        String get(String key) {
            if (map.containsKey(key)) {
                return null
            }
            String value = map.get(key)
            map.remove(key)
            map.put(key, value)
            return value
        }

        add(String key, String value) {
            if (map.containsKey(key)) {
                map.remove(key)
                map.put(key, value)
            }
            if (cap == map.size()) {
                String key1
                for (String key2 : map.keySet()) {
                    key1 = key2
                    break
                }
                map.remove(key1)
                map.put(key, value)
            } else {
                map.put(key, value)
            }
        }
    }
}
