import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IPayrollChangeType } from 'app/shared/model/payroll-change-type.model';
import { getEntities as getPayrollChangeTypes } from 'app/entities/payroll-change-type/payroll-change-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payroll-change-history.reducer';
import { IPayrollChangeHistory } from 'app/shared/model/payroll-change-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayrollChangeHistoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const employees = useAppSelector(state => state.employee.entities);
  const projects = useAppSelector(state => state.project.entities);
  const payrollChangeTypes = useAppSelector(state => state.payrollChangeType.entities);
  const payrollChangeHistoryEntity = useAppSelector(state => state.payrollChangeHistory.entity);
  const loading = useAppSelector(state => state.payrollChangeHistory.loading);
  const updating = useAppSelector(state => state.payrollChangeHistory.updating);
  const updateSuccess = useAppSelector(state => state.payrollChangeHistory.updateSuccess);

  const handleClose = () => {
    props.history.push('/payroll-change-history' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEmployees({}));
    dispatch(getProjects({}));
    dispatch(getPayrollChangeTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...payrollChangeHistoryEntity,
      ...values,
      employee: employees.find(it => it.id.toString() === values.employeeId.toString()),
      manager: employees.find(it => it.id.toString() === values.managerId.toString()),
      project: projects.find(it => it.id.toString() === values.projectId.toString()),
      changeType: payrollChangeTypes.find(it => it.id.toString() === values.changeTypeId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...payrollChangeHistoryEntity,
          date: convertDateTimeFromServer(payrollChangeHistoryEntity.date),
          employeeId: payrollChangeHistoryEntity?.employee?.id,
          managerId: payrollChangeHistoryEntity?.manager?.id,
          projectId: payrollChangeHistoryEntity?.project?.id,
          changeTypeId: payrollChangeHistoryEntity?.changeType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="capachamaApp.payrollChangeHistory.home.createOrEditLabel" data-cy="PayrollChangeHistoryCreateUpdateHeading">
            <Translate contentKey="capachamaApp.payrollChangeHistory.home.createOrEditLabel">
              Create or edit a PayrollChangeHistory
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="payroll-change-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('capachamaApp.payrollChangeHistory.date')}
                id="payroll-change-history-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('capachamaApp.payrollChangeHistory.comments')}
                id="payroll-change-history-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                id="payroll-change-history-employee"
                name="employeeId"
                data-cy="employee"
                label={translate('capachamaApp.payrollChangeHistory.employee')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="payroll-change-history-manager"
                name="managerId"
                data-cy="manager"
                label={translate('capachamaApp.payrollChangeHistory.manager')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="payroll-change-history-project"
                name="projectId"
                data-cy="project"
                label={translate('capachamaApp.payrollChangeHistory.project')}
                type="select"
              >
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="payroll-change-history-changeType"
                name="changeTypeId"
                data-cy="changeType"
                label={translate('capachamaApp.payrollChangeHistory.changeType')}
                type="select"
              >
                <option value="" key="0" />
                {payrollChangeTypes
                  ? payrollChangeTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payroll-change-history" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PayrollChangeHistoryUpdate;
