/*
 * Copyright 2025-2026 ExtraRed
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
 */
package it.extrared.registry.jsonschema;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;

/** A cache for the schema currently in use. */
@ApplicationScoped
public class SchemaCache {

    private final AtomicReference<Uni<Schema>> cached = new AtomicReference<>();

    @Inject JsonSchemaLoaderChain loader;

    /**
     * @return the currently cache schema. If cache is empty the schema is first loaded and then
     *     cached.
     */
    public Uni<Schema> get() {
        Uni<Schema> schema = cached.get();
        if (schema == null) {
            schema = loader.loadSchema().memoize().indefinitely();
            cached.compareAndSet(null, schema);
        }
        return schema;
    }

    /** Invalidates the cache. */
    public void invalidate() {
        cached.set(null);
    }
}
