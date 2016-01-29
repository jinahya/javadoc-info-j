/*
 * Copyright 2016 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jinahya.javadoc.info;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class JavadocInfoTest {


    @Test
    public static void packageNames() throws IOException {
        final String apiUrl = "http://docs.oracle.com/javase/8/docs/api/";
        final List<String> packageNames = JavadocInfo.packageNames(apiUrl);
        System.out.println(packageNames);
    }


    @Test
    public static void getClassNames() throws IOException {
        final String apiUrl = "http://docs.oracle.com/javase/8/docs/api/";
        final String packageName = "java.awt";
        final Map<String, List<String>> classNames
            = JavadocInfo.classNames(apiUrl, packageName);
        System.out.println(classNames);
    }


    @Test
    public static void javase8() throws IOException {
        final String apiUrl = "http://docs.oracle.com/javase/8/docs/api/";
        final List<String> packageNames = JavadocInfo.packageNames(apiUrl);
        for (final String packageName : packageNames) {
            final Map<String, List<String>> classNames
                = JavadocInfo.classNames(apiUrl, packageName);
        }
    }


    @Test
    public static void javase7() throws IOException {
        final String apiUrl = "http://docs.oracle.com/javase/7/docs/api/";
        final List<String> packageNames = JavadocInfo.packageNames(apiUrl);
        for (final String packageName : packageNames) {
            final Map<String, List<String>> classNames
                = JavadocInfo.classNames(apiUrl, packageName);
        }
    }

}

