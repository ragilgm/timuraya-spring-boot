package timuraya.invoice_biaya_operational.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 19/06/22
 **/

public class SearchCriteria<T> implements Serializable {
    private static final long serialVersionUID = 2405172041950251807L;
    private String key;
    private T value;
    private SearchCriteria.SearchOperation operation;

    public SearchCriteria(String key, T value, SearchCriteria.SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return this.key;
    }

    public T getValue() {
        return this.value;
    }

    public SearchCriteria.SearchOperation getOperation() {
        return this.operation;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public void setOperation(final SearchCriteria.SearchOperation operation) {
        this.operation = operation;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SearchCriteria)) {
            return false;
        } else {
            SearchCriteria<?> other = (SearchCriteria)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$key = this.getKey();
                    Object other$key = other.getKey();
                    if (this$key == null) {
                        if (other$key == null) {
                            break label47;
                        }
                    } else if (this$key.equals(other$key)) {
                        break label47;
                    }

                    return false;
                }

                Object this$value = this.getValue();
                Object other$value = other.getValue();
                if (this$value == null) {
                    if (other$value != null) {
                        return false;
                    }
                } else if (!this$value.equals(other$value)) {
                    return false;
                }

                Object this$operation = this.getOperation();
                Object other$operation = other.getOperation();
                if (this$operation == null) {
                    if (other$operation != null) {
                        return false;
                    }
                } else if (!this$operation.equals(other$operation)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SearchCriteria;
    }



    public String toString() {
        String var10000 = this.getKey();
        return "SearchCriteria(key=" + var10000 + ", value=" + this.getValue() + ", operation=" + this.getOperation() + ")";
    }

    public static enum SearchOperation {
        GREATER_THAN {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.greaterThan(root.get(criteria.getKey()).as(String.class), criteria.getValue().toString()));
            }
        },
        LESS_THAN {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.lessThan(root.get(criteria.getKey()).as(String.class), criteria.getValue().toString()));
            }
        },
        GREATER_THAN_EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()).as(String.class), criteria.getValue().toString()));
            }
        },
        LESS_THAN_EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()).as(String.class), criteria.getValue().toString()));
            }
        },
        NOT_EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.notEqual(root.get(criteria.getKey()).as(String.class), criteria.getValue().toString()));
            }
        },
        EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.equal(root.get(criteria.getKey()).as(String.class), criteria.getValue().toString()));
            }
        },
        MATCH {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey()).as(String.class)), "%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
        },
        MATCH_END {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey()).as(String.class)), criteria.getValue().toString().toLowerCase() + "%"));
            }
        },
        EQUAL_IGNORE_CASE {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.equal(builder.lower(root.get(criteria.getKey()).as(String.class)), criteria.getValue().toString().toLowerCase()));
            }
        };

        private SearchOperation() {
        }

        public abstract void getOperation(List<Predicate> predicates, CriteriaBuilder builder, SearchCriteria<?> criteria, Root<?> root);
    }
}



