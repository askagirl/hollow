/*
 *
 *  Copyright 2016 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.hollow.core.write.objectmapper;

import com.netflix.hollow.core.util.HollowObjectHashCodeFinder;

import com.netflix.hollow.core.schema.HollowMapSchema;
import com.netflix.hollow.core.write.HollowMapTypeWriteState;
import com.netflix.hollow.core.write.HollowMapWriteRecord;
import com.netflix.hollow.core.write.HollowTypeWriteState;
import com.netflix.hollow.core.write.HollowWriteRecord;
import com.netflix.hollow.core.write.HollowWriteStateEngine;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class HollowMapTypeMapper extends HollowTypeMapper {

    private final HollowMapSchema schema;
    private final HollowMapTypeWriteState writeState;

    private final HollowObjectHashCodeFinder hashCodeFinder;
    
    private HollowTypeMapper keyMapper;
    private HollowTypeMapper valueMapper;

    public HollowMapTypeMapper(HollowObjectMapper parentMapper, ParameterizedType type, String declaredName, String[] hashKeyFieldPaths, int numShards, HollowWriteStateEngine stateEngine, boolean useDefaultHashKeys, Set<Type> visited) {
        this.keyMapper = parentMapper.getTypeMapper(type.getActualTypeArguments()[0], null, null, -1, visited);
        this.valueMapper = parentMapper.getTypeMapper(type.getActualTypeArguments()[1], null, null, -1, visited);
        String typeName = declaredName != null ? declaredName : getDefaultTypeName(type);
        
        if(hashKeyFieldPaths == null && useDefaultHashKeys && (keyMapper instanceof HollowObjectTypeMapper))
            hashKeyFieldPaths = ((HollowObjectTypeMapper)keyMapper).getDefaultElementHashKey();
        
        this.schema = new HollowMapSchema(typeName, keyMapper.getTypeName(), valueMapper.getTypeName(), hashKeyFieldPaths);
        this.hashCodeFinder = stateEngine.getHashCodeFinder();

        HollowMapTypeWriteState typeState = (HollowMapTypeWriteState) parentMapper.getStateEngine().getTypeState(typeName);
        this.writeState = typeState != null ? typeState : new HollowMapTypeWriteState(schema, numShards);
    }

    @Override
    protected String getTypeName() {
        return schema.getName();
    }

    @Override
    protected int write(Object obj) {
        Map<?, ?> m = (Map<?, ?>)obj;

        HollowMapWriteRecord rec = (HollowMapWriteRecord)writeRecord();
        for(Map.Entry<?, ?>entry : m.entrySet()) {
            int keyOrdinal = keyMapper.write(entry.getKey());
            int valueOrdinal = valueMapper.write(entry.getValue());
            int hashCode = hashCodeFinder.hashCode(keyMapper.getTypeName(), keyOrdinal, entry.getKey());

            rec.addEntry(keyOrdinal, valueOrdinal, hashCode);
        }

        return writeState.add(rec);
    }

    @Override
    protected HollowWriteRecord newWriteRecord() {
        return new HollowMapWriteRecord();
    }

    @Override
    protected HollowTypeWriteState getTypeWriteState() {
        return writeState;
    }

}
