package com.jayomail.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class Template {
    protected String content;

    public abstract Template setTemplate(String content);

    public abstract Template setValue(String key, String value);

    public abstract String build();
    
    public abstract String getContent();
}


