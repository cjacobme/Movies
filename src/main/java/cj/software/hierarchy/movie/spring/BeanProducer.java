package cj.software.hierarchy.movie.spring;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class BeanProducer {
    @Bean
    public FakeValuesService faker() {
        FakeValuesService result = new FakeValuesService(Locale.ENGLISH, new RandomService());
        return result;
    }
}
