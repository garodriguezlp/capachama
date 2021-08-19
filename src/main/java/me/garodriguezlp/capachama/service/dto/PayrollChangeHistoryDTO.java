package me.garodriguezlp.capachama.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link me.garodriguezlp.capachama.domain.PayrollChangeHistory} entity.
 */
public class PayrollChangeHistoryDTO implements Serializable {

    private Long id;

    private Instant date;

    private String comments;

    private EmployeeDTO employee;

    private EmployeeDTO manager;

    private ProjectDTO project;

    private PayrollChangeTypeDTO changeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public PayrollChangeTypeDTO getChangeType() {
        return changeType;
    }

    public void setChangeType(PayrollChangeTypeDTO changeType) {
        this.changeType = changeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayrollChangeHistoryDTO)) {
            return false;
        }

        PayrollChangeHistoryDTO payrollChangeHistoryDTO = (PayrollChangeHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, payrollChangeHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayrollChangeHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", employee=" + getEmployee() +
            ", manager=" + getManager() +
            ", project=" + getProject() +
            ", changeType=" + getChangeType() +
            "}";
    }
}
