package pl.umcs.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.basis.lenderinfo.Lender;

@Repository
public interface LenderRepository extends CrudRepository<Lender,Long> {
}
