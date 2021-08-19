package me.garodriguezlp.capachama.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PayrollChangeHistory.
 */
@Entity
@Table(name = "payroll_change_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayrollChangeHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "comments")
    private String comments;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee manager;

    @OneToOne
    @JoinColumn(unique = true)
    private Project project;

    @OneToOne
    @JoinColumn(unique = true)
    private PayrollChangeType changeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PayrollChangeHistory id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public PayrollChangeHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public PayrollChangeHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getComments() {
        return this.comments;
    }

    public PayrollChangeHistory comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public PayrollChangeHistory employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getManager() {
        return this.manager;
    }

    public PayrollChangeHistory manager(Employee employee) {
        this.setManager(employee);
        return this;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    public Project getProject() {
        return this.project;
    }

    public PayrollChangeHistory project(Project project) {
        this.setProject(project);
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PayrollChangeType getChangeType() {
        return this.changeType;
    }

    public PayrollChangeHistory changeType(PayrollChangeType payrollChangeType) {
        this.setChangeType(payrollChangeType);
        return this;
    }

    public void setChangeType(PayrollChangeType payrollChangeType) {
        this.changeType = payrollChangeType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayrollChangeHistory)) {
            return false;
        }
        return id != null && id.equals(((PayrollChangeHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayrollChangeHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
