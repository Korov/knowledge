package com.example.flux.httpd.json;

import com.example.flux.httpd.json.parser.Parser;
import com.example.flux.httpd.json.tokenizer.CharReader;
import com.example.flux.httpd.json.tokenizer.TokenList;
import com.example.flux.httpd.json.tokenizer.Tokenizer;


import java.io.IOException;
import java.io.StringReader;

/**
 * Created by code4wt on 17/9/1.
 */
public class JSONParser {

    private Tokenizer tokenizer = new Tokenizer();

    private Parser parser = new Parser();

    public Object fromJSON(String json) throws IOException {
        CharReader charReader = new CharReader(new StringReader(json));
        TokenList tokens = tokenizer.tokenize(charReader);
        return parser.parse(tokens);
    }
}
