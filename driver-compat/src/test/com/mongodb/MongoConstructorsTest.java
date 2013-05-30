/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb;

import org.junit.Ignore;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class MongoConstructorsTest {

    @Test
    @Ignore
    public void shouldDefaultToLocalhost() throws UnknownHostException {
        final Mongo mongo = new MongoClient();
        try {
            assertEquals(Arrays.asList(new ServerAddress()), mongo.getServerAddressList());
        } finally {
            mongo.close();
        }
    }

    @Test
    @Ignore
    public void shouldUseGivenHost() throws UnknownHostException {
        final Mongo mongo = new MongoClient("localhost");
        try {
            assertEquals(Arrays.asList(new ServerAddress("localhost")), mongo.getServerAddressList());
        } finally {
            mongo.close();
        }
    }

    @Test
    @Ignore
    public void shouldUseGivenServerAddress() throws UnknownHostException {
        final Mongo mongo = new MongoClient(new ServerAddress("localhost"));
        try {
            assertEquals(Arrays.asList(new ServerAddress("localhost")), mongo.getServerAddressList());
        } finally {
            mongo.close();
        }
    }

    @Test
    public void shouldDefaultToPrimaryReadPreference() throws UnknownHostException {
        final Mongo mongo = new MongoClient();
        try {
            assertEquals(ReadPreference.primary(), mongo.getReadPreference());
        } finally {
            mongo.close();
        }
    }

    @Test
    public void shouldSaveDefaultReadPreference() throws UnknownHostException {
        final Mongo mongo = new MongoClient();
        mongo.setReadPreference(ReadPreference.nearest());
        assertEquals(ReadPreference.nearest(), mongo.getReadPreference());
    }

    @Test
    public void shouldSaveDefaultWriteConcern() throws UnknownHostException {
        final Mongo mongo = new MongoClient();
        try {
            mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
            assertEquals(WriteConcern.ACKNOWLEDGED, mongo.getWriteConcern());
        } finally {
            mongo.close();
        }
    }

    @Test
    public void shouldGetDB() throws UnknownHostException {
        final Mongo mongo = new MongoClient();
        try {
            final DB db = mongo.getDB("test");
            assertNotNull(db);
            assertEquals("test", db.getName());
        } finally {
            mongo.close();
        }
    }

    @Test
    public void shouldGetSameDB() throws UnknownHostException {
        final Mongo mongo = new MongoClient();
        try {
            assertSame(mongo.getDB("test"), mongo.getDB("test"));
        } finally {
            mongo.close();
        }
    }
}
