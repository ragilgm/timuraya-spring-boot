package timuraya.invoice_biaya_operational.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericSpecificationService<T> implements Specification<T> {
    private static final long serialVersionUID = 1900581010229669687L;
    private List<SearchCriteria<?>> searchCriteriaList = new ArrayList();
    private Specification<T> specification;

    public GenericSpecificationService() {
    }

    public GenericSpecificationService(Specification<T> specification) {
        this.specification = specification;
    }

    public void add(SearchCriteria<?> criteria) {
        if (!Objects.isNull(criteria.getValue())) {
            this.searchCriteriaList.add(criteria);
        }

    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList();
        if (this.specification != null) {
            predicates.add(this.specification.toPredicate(root, query, builder));
        }

        this.searchCriteriaList.forEach((criteria) -> {
            criteria.getOperation().getOperation(predicates, builder, criteria, root);
        });
        return builder.and((Predicate[])predicates.toArray(new Predicate[0]));
    }
}

