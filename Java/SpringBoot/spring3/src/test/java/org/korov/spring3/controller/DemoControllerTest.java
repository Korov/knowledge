package org.korov.spring3.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.korov.spring3.Spring3ApplicationTests;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhu.lei
 * @date 2022-11-25 12:23
 */
class DemoControllerTest extends Spring3ApplicationTests {

    @ParameterizedTest
    @ValueSource(strings = {
            "korov"
    })
    void hello(String content) throws Exception {
        String response = this.mockMvc
                .perform(get(String.format("/hello?name=%s", content)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }
}