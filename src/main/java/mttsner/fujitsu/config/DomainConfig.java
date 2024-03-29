package mttsner.fujitsu.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("mttsner.fujitsu")
@EnableJpaRepositories("mttsner.fujitsu")
@EnableTransactionManagement
public class DomainConfig {
}