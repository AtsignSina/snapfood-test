package ir.atsignsina.task.snapfood.app.faker;

import com.vastik.spring.data.faker.DataFaker;
import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import ir.atsignsina.task.snapfood.domain.agent.Agent;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.vendor.Vendor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class Fakers implements CommandLineRunner {
    private final DataFaker dataFaker = new DataFaker();
    @PersistenceContext
    EntityManager entityManager;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private FakerTableCommand apply(String[] s) {
        FakerTable ft;
        try {
            ft = FakerTable.valueOf(s[0].trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
        int count = 1;
        if (s.length > 1) {
            try {
                count = Integer.parseInt(s[1]);
            } catch (NumberFormatException ignored) {
            }
        }
        return new FakerTableCommand(ft, count);
    }

    @Override
    @Transactional
    public void run(String... args) {
        FakerTableCommand[] fakerTable = getFakerTables(args);
        Session session = ((Session) entityManager.getDelegate());
        for (FakerTableCommand ftc : fakerTable) {
            function(ftc.getFakerTable())
                    .apply(ftc)
                    .forEach(session::persist);
        }
        session.flush();
    }

    private FakerTableCommand[] getFakerTables(String[] args) {
        FakerTableCommand[] tables = new FakerTableCommand[0];
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.trim().equalsIgnoreCase("--FAKE")) {
                if (i == args.length - 1) {
                    throw new RuntimeException("Fake tables is not mentioned.");
                } else {
                    String[] str = args[i + 1].trim().split(",");
                    tables = Stream.of(str).map(s -> s.trim().split(":")).map(this::apply)
                            .toArray(FakerTableCommand[]::new);
                }
            }
        }
        return tables;
    }

    private List<AbstractEntity> order(FakerTableCommand ftc) {
        List<AbstractEntity> list = new ArrayList<>();
        Session session = ((Session) entityManager.getDelegate());
        List<Vendor> vendors = session.createQuery(
                        "select v from ir.atsignsina.task.snapfood.domain.vendor.Vendor v",
                        Vendor.class
                )
                .getResultList();
        try {
            for (int i = 0; i < ftc.getCount(); i++) {
                Order o = (Order) dataFaker.fake(ftc.getFakerTable().getClazz());
                o.setVendor(vendors.get(SECURE_RANDOM.nextInt(vendors.size())));
                list.add(o);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;

    }

    private List<AbstractEntity> abstractEntity(FakerTableCommand ftc) {
        List<AbstractEntity> list = new ArrayList<>();
        try {
            for (int i = 0; i < ftc.getCount(); i++) {
                AbstractEntity o = (AbstractEntity) dataFaker.fake(ftc.getFakerTable().getClazz());
                list.add(o);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    Function<FakerTableCommand, List<AbstractEntity>> function(FakerTable ft) {
        return switch (ft) {
            case agent, vendor -> this::abstractEntity;
            case order -> this::order;
        };
    }

    @Getter
    enum FakerTable {
        agent(Agent.class),
        vendor(Vendor.class),
        order(Order.class);
        private final Class<?> clazz;

        FakerTable(Class<?> clazz) {
            this.clazz = clazz;
        }
    }

    @AllArgsConstructor
    @Getter
    class FakerTableCommand {
        private final FakerTable fakerTable;
        private final int count;
    }
}



