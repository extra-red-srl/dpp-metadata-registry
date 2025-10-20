package it.extrared.registry;

import io.quarkus.runtime.util.StringUtil;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import io.smallrye.config.WithDefault;
import it.extrared.registry.metadata.update.UpdateType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.eclipse.microprofile.config.spi.Converter;

@ConfigMapping(prefix = "registry")
public interface MetadataRegistryConfig {

    @WithConverter(ListStringConverter.class)
    Optional<List<String>> autocompletionEnabledFor();

    @WithDefault("MODIFY")
    @WithConverter(UpdateStrategyConverter.class)
    UpdateType updateStrategy();

    @WithDefault("upi")
    String upiFieldName();

    Optional<String> jsonSchemaLocation();

    class UpdateStrategyConverter implements Converter<UpdateType> {

        @Override
        public UpdateType convert(String s) throws IllegalArgumentException, NullPointerException {
            if (StringUtil.isNullOrEmpty(s)) return null;
            String up = s.toUpperCase();
            List<String> strVals = Stream.of(UpdateType.values()).map(Enum::name).toList();
            if (!strVals.contains(up)) {
                throw new IllegalArgumentException(
                        "update-strategy property must be equal to one of this values %s"
                                .formatted(String.join(",", strVals)));
            }
            return UpdateType.valueOf(s.toUpperCase());
        }
    }

    class ListStringConverter implements Converter<List<String>> {

        @Override
        public List<String> convert(String s)
                throws IllegalArgumentException, NullPointerException {
            if (!StringUtil.isNullOrEmpty(s)) return Arrays.asList(s.split(","));
            return Collections.emptyList();
        }
    }
}
