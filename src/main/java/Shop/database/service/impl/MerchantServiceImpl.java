package Shop.database.service.impl;

import Shop.database.repository.MerchantRepository;
import Shop.database.service.api.MerchantService;
import Shop.domain.Merchant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<Merchant> getMerchants() {
       return merchantRepository.getMerchants();
    }

    @Override
    public Merchant getMerchant(int id) {
        return merchantRepository.getMerchant(id);
    }

    @Override
    public Integer addMerchant(Merchant merchant) {
       return merchantRepository.addMerchant(merchant);
    }
}
