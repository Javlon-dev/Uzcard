package com.company;

import com.company.repository.BankRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class Lesson111UzcardApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Lesson111UzcardApplication.class, args);
        String bankCard = context.getEnvironment().getProperty("message.bank.card");
        String keyWord = context.getEnvironment().getProperty("message.bank.key.word");
        BankRepository repository = context.getBean(BankRepository.class);
        if (Optional.ofNullable(repository.findByBankCardNumber(bankCard)).isEmpty()){
            repository.bankCard(keyWord, bankCard);
        }
    }

}
