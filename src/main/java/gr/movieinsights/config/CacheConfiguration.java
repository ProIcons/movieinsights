package gr.movieinsights.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, gr.movieinsights.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, gr.movieinsights.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, gr.movieinsights.domain.User.class.getName());
            createCache(cm, gr.movieinsights.domain.Authority.class.getName());
            createCache(cm, gr.movieinsights.domain.User.class.getName() + ".authorities");
            createCache(cm, gr.movieinsights.domain.Movie.class.getName());
            createCache(cm, gr.movieinsights.domain.Movie.class.getName() + ".credits");
            createCache(cm, gr.movieinsights.domain.Movie.class.getName() + ".banReasons");
            createCache(cm, gr.movieinsights.domain.Movie.class.getName() + ".companies");
            createCache(cm, gr.movieinsights.domain.Movie.class.getName() + ".countries");
            createCache(cm, gr.movieinsights.domain.Movie.class.getName() + ".genres");
            createCache(cm, gr.movieinsights.domain.Person.class.getName());
            createCache(cm, gr.movieinsights.domain.Person.class.getName() + ".credits");
            createCache(cm, gr.movieinsights.domain.Person.class.getName() + ".banReasons");
            createCache(cm, gr.movieinsights.domain.Credit.class.getName());
            createCache(cm, gr.movieinsights.domain.BannedEntity.class.getName());
            createCache(cm, gr.movieinsights.domain.Genre.class.getName());
            createCache(cm, gr.movieinsights.domain.Genre.class.getName() + ".movies");
            createCache(cm, gr.movieinsights.domain.Vote.class.getName());
            createCache(cm, gr.movieinsights.domain.ProductionCountry.class.getName());
            createCache(cm, gr.movieinsights.domain.ProductionCountry.class.getName() + ".movies");
            createCache(cm, gr.movieinsights.domain.ProductionCompany.class.getName());
            createCache(cm, gr.movieinsights.domain.ProductionCompany.class.getName() + ".banReasons");
            createCache(cm, gr.movieinsights.domain.ProductionCompany.class.getName() + ".movies");
            createCache(cm, gr.movieinsights.domain.MovieInsights.class.getName());
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerCountry.class.getName());
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerCountry.class.getName() + ".movieInsightsPerYears");
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerCompany.class.getName());
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerCompany.class.getName() + ".movieInsightsPerYears");
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerPerson.class.getName());
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerPerson.class.getName() + ".movieInsightsPerYears");
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerGenre.class.getName());
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerGenre.class.getName() + ".movieInsightsPerYears");
            createCache(cm, gr.movieinsights.domain.MovieInsightsPerYear.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
