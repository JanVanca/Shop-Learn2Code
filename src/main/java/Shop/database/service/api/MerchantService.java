package Shop.database.service.api;

import Shop.domain.Merchant;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MerchantService {

    List<Merchant> getMerchants();
    @NonNull
    Merchant getMerchant(int id);
    @NonNull
    Integer addMerchant(Merchant merchant);
}
