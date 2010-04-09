/**
 * Copyright (c) 2008-2010 Andrey Somov
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
package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.error.YAMLException;

public class IgnoringConstructor extends Constructor {
    private Construct original;

    public IgnoringConstructor() {
        original = this.yamlConstructors.get(null);
        this.yamlConstructors.put(null, new IgnoringConstruct());
    }

    private class IgnoringConstruct extends AbstractConstruct {
        public Object construct(Node node) {
            if (node.getTag().startsWith("!KnownTag")) {
                return original.construct(node);
            } else {
                switch (node.getNodeId()) {
                case scalar:
                    return yamlConstructors.get(Tag.STR).construct(node);
                case sequence:
                    return yamlConstructors.get(Tag.SEQ).construct(node);
                case mapping:
                    return yamlConstructors.get(Tag.MAP).construct(node);
                default:
                    throw new YAMLException("Unexpected node");
                }
            }
        }
    }
}
