package ke.co.phyno.learn.r2dbc.store.repo;

import ke.co.phyno.learn.r2dbc.store.model.CustomerProfile;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileRepository extends ReactiveSortingRepository<CustomerProfile, Long> {
}
