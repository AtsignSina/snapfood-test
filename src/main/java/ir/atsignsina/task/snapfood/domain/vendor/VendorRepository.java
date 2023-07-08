package ir.atsignsina.task.snapfood.domain.vendor;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface VendorRepository extends PagingAndSortingRepository<Vendor, UUID> {}
