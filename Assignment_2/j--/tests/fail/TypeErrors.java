// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package fail;

import java.lang.System;
import java.lang.Integer;

// This program has type errors and shouldn't compile.

public class TypeErrors {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(n == true);
    }

}
