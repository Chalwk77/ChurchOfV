// Copyright (c) 2023, Jericho Crosby <jericho.crosby227@gmail.com>

package com.chalwk.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Authentication {
    public static String getToken() throws IOException {
        BufferedReader text = new BufferedReader(new FileReader("auth.token"));
        return text.readLine();
    }
}
