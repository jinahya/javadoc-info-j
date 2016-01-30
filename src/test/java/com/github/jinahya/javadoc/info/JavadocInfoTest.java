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
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class JavadocInfoTest {


    private static final Logger logger = getLogger(JavadocInfoTest.class);


    @DataProvider
    static Object[][] apiUrls() {
        return new Object[][]{
            {"http://docs.oracle.com/javase/8/docs/api/"},
            {"http://docs.oracle.com/javase/8/docs/api/"}
        };
    }


    @DataProvider
    static Object[][] artifacts() {
        return new Object[][]{
            {"com.google.guava", "guava", "19.0"}
        };
    }


    @Test(dataProvider = "apiUrls")
    public static void apiUrl(final String apiUrl) throws IOException {

        final List<String> packageNames = JavadocInfo.packageNames(apiUrl);
        assertFalse(packageNames.isEmpty());

        for (final String packageName : packageNames) {
            final Map<String, List<String>> classNames = JavadocInfo.classNames(
                apiUrl, packageName);
            assertFalse(classNames.isEmpty());
        }
    }


    @Test(dataProvider = "artifacts")
    public static void artifact(final String groupId, final String artifactId,
                                final String version)
        throws IOException {

        final List<String> packageNames
            = JavadocInfo.packageNames(groupId, artifactId, version);
        assertFalse(packageNames.isEmpty());

        for (final String packageName : packageNames) {
            final Map<String, List<String>> classNames = JavadocInfo.classNames(
                groupId, artifactId, version, packageName);
            assertFalse(classNames.isEmpty());
        }
    }

}

