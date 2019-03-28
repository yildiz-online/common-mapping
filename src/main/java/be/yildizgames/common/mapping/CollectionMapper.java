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


import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.mapping.exception.MappingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class must be sub-classed
 * @author Grégory Van den Borre
 */
public class CollectionMapper<T> implements ObjectMapper<Collection<T>> {

    private final ObjectMapper<T> mapper;

    public CollectionMapper(ObjectMapper<T> mapper) {
        super();
        this.mapper = mapper;
    }

    @Override
    public final Collection<T> from(String s) {
        ImplementationException.throwForNull(s);
        if(s.isEmpty()) {
            return Collections.emptyList();
        }
        String[] values = s.split(Separator.COLLECTION_SEPARATOR);
        List<T> result = new ArrayList<>(values.length);
        for(String value : values) {
            result.add(this.mapper.from(value));
        }
        return result;
    }

    @Override
    public final String to(Collection<T> collection) {
        ImplementationException.throwForNull(collection);
        final List<T> l = new ArrayList<>(collection);
        final StringBuilder sb = new StringBuilder();
        for (T t : l) {
            sb.append(this.mapper.to(t));
            sb.append(Separator.COLLECTION_SEPARATOR);
        }
        if (sb.charAt(sb.length() - 1) == Separator.COLLECTION_SEPARATOR.charAt(0)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
