/*
 * Copyright 2017 Otavio Santana and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package org.jnosql.diana.api.reader;


import org.jnosql.diana.api.ValueReader;

import static java.lang.Character.MIN_VALUE;

/**
 * Class reader for {@link Character}
 */
@SuppressWarnings("unchecked")
public final class CharacterValueReader implements ValueReader {

    @Override
    public <T> boolean isCompatible(Class<T> clazz) {
        return Character.class.equals(clazz) || char.class.equals(clazz);
    }

    @Override
    public <T> T read(Class<T> clazz, Object value) {
        if (Character.class.isInstance(value)) {
            return (T) value;
        }
        if (Number.class.isInstance(value)) {
            return (T) Character.valueOf((char) Number.class.cast(value).intValue());
        }

        if (value.toString().isEmpty()) {
            return (T) Character.valueOf(MIN_VALUE);
        }
        return (T) Character.valueOf(value.toString().charAt(0));
    }


}
