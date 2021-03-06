package lf65.ams.infrastructure;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lf65.ams.domain.Error;
import lf65.ams.infrastructure.gson.ErrorsTypeAdapter;
import lf65.ams.infrastructure.gson.LinksArrayTypeAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Type;
import java.util.List;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static java.util.Arrays.asList;

@Configuration
@EnableWebMvc
public class AmsWebMvcConfiguration implements WebMvcConfigurer {

    private Gson gson() {
        final Type linkListType = new TypeToken<List<Link>>() {
        }.getType();
        final Type errorListType = new TypeToken<List<Error>>() {
        }.getType();
        return new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(linkListType, new LinksArrayTypeAdapter())
                .registerTypeAdapter(errorListType, new ErrorsTypeAdapter())
                .create();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        gsonHttpMessageConverter.setSupportedMediaTypes(asList(MediaType.APPLICATION_JSON));
        converters.add(gsonHttpMessageConverter);

    }
}
