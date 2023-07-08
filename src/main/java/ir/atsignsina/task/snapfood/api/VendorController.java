package ir.atsignsina.task.snapfood.api;

import ir.atsignsina.task.snapfood.app.service.DataService;
import ir.atsignsina.task.snapfood.domain.vendor.Vendor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vendors")
public class VendorController extends DomainController<Vendor> {
    public VendorController(DataService dataService) {
        super(dataService);
    }

    @Override
    Class<Vendor> getClazz() {
        return Vendor.class;
    }
}
