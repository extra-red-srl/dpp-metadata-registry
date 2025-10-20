package it.extrared.registry.exceptions;

import com.networknt.schema.ValidationMessage;
import java.util.Set;

/**
 * Exception used to signal the failure of a JSON schema validation performed over a JSON document.
 */
public class SchemaValidationException extends RuntimeException {

    public SchemaValidationException(Set<ValidationMessage> messages) {
        super(errorMessage(messages));
    }

    private static String errorMessage(Set<ValidationMessage> messages) {
        String msg =
                String.join(
                        "\n",
                        messages.stream().map(SchemaValidationException::formatError).toList());
        return String.format(
                "Json schema validation returned the following errors: %s".formatted(msg));
    }

    private static String formatError(ValidationMessage vm) {
        return String.format("Path: %s - Error: %s", vm.getProperty(), vm.getMessage());
    }
}
