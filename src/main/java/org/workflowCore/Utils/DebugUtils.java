// java
package org.workflowCore.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class DebugUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String MASK = "*****";
    private static final String[] SENSITIVE_KEYS = { "password", "pass", "pwd", "token", "secret", "auth" };

    private DebugUtils() {}

    /**
     * Convert object to pretty JSON, mask common sensitive fields and print.
     * Intended for debugging only.
     */
    public static void printAsJson(Object obj) {
        System.out.println(toJsonMasked(obj));
    }

    /**
     * Returns pretty JSON string with sensitive fields masked.
     */
    public static String toJsonMasked(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            JsonNode node = MAPPER.valueToTree(obj);
            maskSensitive(node);
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IllegalArgumentException | JsonProcessingException e) {
            // fallback: print object's toString() if conversion fails
            return obj.toString();
        }
    }

    private static void maskSensitive(JsonNode node) {
        if (node == null) return;

        if (node.isObject()) {
            ObjectNode obj = (ObjectNode) node;
            var it = obj.fieldNames();
            while (it.hasNext()) {
                String field = it.next();
                JsonNode child = obj.get(field);
                if (isSensitiveKey(field)) {
                    obj.put(field, MASK);
                } else {
                    maskSensitive(child);
                }
            }
        } else if (node.isArray()) {
            ArrayNode arr = (ArrayNode) node;
            for (int i = 0; i < arr.size(); i++) {
                maskSensitive(arr.get(i));
            }
        }
    }

    private static boolean isSensitiveKey(String key) {
        if (key == null) return false;
        String lower = key.toLowerCase();
        for (String s : SENSITIVE_KEYS) {
            if (lower.contains(s)) return true;
        }
        return false;
    }
}
