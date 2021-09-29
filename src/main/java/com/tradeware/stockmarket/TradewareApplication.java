package com.tradeware.stockmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*@SpringBootApplication
@ComponentScan({"com.tradeware"})
@EntityScan("com.tradeware.clouddatabase.entity")
@EnableJpaRepositories("com.tradeware.clouddatabase.repository")*/

@SpringBootApplication
@ComponentScan({"com.tradeware", "org.tradeware"})
@EntityScan({"com.tradeware.clouddatabase.entity", "org.tradeware.stockmarket.tradewaredatabase.entity"})
@EnableJpaRepositories({"com.tradeware.clouddatabase.repository", "org.tradeware.stockmarket.tradewaredatabase.repository"})
public class TradewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradewareApplication.class, args);
	}

}
