/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.common.mapping;

import be.yildizgames.common.mapping.exception.MappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class CollectionMapperTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            assertNotNull(new CollectionMapper<Float>(FloatMapper.getInstance()));
        }

        @Test
        void nullParameter() {
            assertThrows(NullPointerException.class, () -> new CollectionMapper<Float>(null));
        }
    }

    @Nested
    class From {

        @Test
        void happyFlow() throws MappingException {
            CollectionMapper<Integer> mapper = new CollectionMapper<>(IntegerMapper.getInstance());
            Collection<Integer> ints = mapper.from("5,2,3");
            assertEquals(3, ints.size());
            Iterator<Integer> it = ints.iterator();
            assertEquals(5, it.next());
            assertEquals(2, it.next());
            assertEquals(3, it.next());
        }

        @Test
        void invalidValue() throws MappingException {
            CollectionMapper<Integer> mapper = new CollectionMapper<>(IntegerMapper.getInstance());
            Assertions.assertThrows(MappingException.class, () -> mapper.from("5a,2,3"));
        }

        @Test
        void withNull() throws MappingException {
            assertThrows(NullPointerException.class, () -> FloatMapper.getInstance().from(null));
        }
    }

    @Nested
    class To {

        @Test
        void happyFlow() {
            CollectionMapper<Float> mapper = new CollectionMapper<>(FloatMapper.getInstance());
            assertEquals("5.0,2.0,3.0", mapper.to(List.of(5f, 2f, 3f)));
        }

        @Test
        void withNull() {
            assertThrows(NullPointerException.class, () -> FloatMapper.getInstance().to(null));
        }
    }
}
