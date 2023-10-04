package com.wfrequency.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface FileService {
    Map<String, Integer> calculateWordFrequency(BufferedReader reader) throws IOException;


}
