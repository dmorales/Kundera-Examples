/*
 * Copyright 2011 Impetus Infotech.
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
package com.impetus.kundera.examples.utils;

import java.util.Date;
import java.util.UUID;

/**
 * Class for utility methods
 * 
 * @author amresh.singh
 */
public class ExampleUtils
{
    public static String getUniqueId()
    {
        return UUID.randomUUID().toString();
    }

    public static long getCurrentTimestamp()
    {
        return new Date().getTime();
    }

    public static void main(String[] args)
    {
        System.out.println(getCurrentTimestamp());
    }

}
