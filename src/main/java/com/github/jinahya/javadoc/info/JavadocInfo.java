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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class JavadocInfo {


    public static String apiUrl(final String groupId, final String artifactId,
                                final String version)
        throws IOException {

        return "http://static.javadoc.io"
               + "/" + groupId
               + "/" + artifactId
               + "/" + version;
    }


    public static List<String> packageNames(String apiUrl) throws IOException {

        if (apiUrl.endsWith("/")) {
            apiUrl = apiUrl.substring(0, apiUrl.length() - 1);
        }

        final List<String> packageNames = new ArrayList<>();

        final String url = apiUrl + "/package-list";
        final HttpURLConnection connection
            = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty(
            "User-Agent", JavadocInfo.class.getName());
        connection.connect();
        try {
            final BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTF-8"));
            try {
                for (String line; (line = reader.readLine()) != null;) {
                    packageNames.add(line);
                }
            } finally {
                reader.close();
            }
        } finally {
            connection.disconnect();
        }

        return packageNames;
    }


    public static List<String> packageNames(final String groupId,
                                            final String artifactId,
                                            final String version)
        throws IOException {

        return packageNames(apiUrl(groupId, artifactId, version));
    }


    public static Map<String, List<String>> classNames(
        String apiUrl, String packageName)
        throws IOException {

        if (apiUrl.endsWith("/")) {
            apiUrl = apiUrl.substring(0, apiUrl.length() - 1);
        }
        packageName = packageName.replace('.', '/');

        final Map<String, List<String>> classNames = new HashMap<>();

        final Document document = Jsoup
            .connect(apiUrl + "/" + packageName + "/package-frame.html")
            .userAgent(JavadocInfo.class.getName())
            .get();
        final Elements es1 = document.select("ul[title]");
        for (Iterator<Element> i = es1.iterator(); i.hasNext();) {
            final Element e1 = i.next();
            final String title = e1.attr("title");
            final List<String> list = new ArrayList<>();
            classNames.put(title, list);
            final Elements es2 = e1.select("a[href]");
            for (Iterator<Element> i2 = es2.iterator(); i2.hasNext();) {
                final Element e2 = i2.next();
                list.add(e2.text());
            }
        }

        return classNames;
    }


    public static Map<String, List<String>> classNames(
        final String groupId, final String artifactId, final String version,
        final String packageName)
        throws IOException {

        return classNames(apiUrl(groupId, artifactId, version), packageName);
    }


    private JavadocInfo() {

        super();
    }

}

