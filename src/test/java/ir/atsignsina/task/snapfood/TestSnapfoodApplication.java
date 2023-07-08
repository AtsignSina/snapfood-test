package ir.atsignsina.task.snapfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSnapfoodApplication {

	public static void main(String[] args) {
		SpringApplication.from(SnapfoodApplication::main).with(TestSnapfoodApplication.class).run(args);
	}

}
