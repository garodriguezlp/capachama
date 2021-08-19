import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './payroll-change-history.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayrollChangeHistoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const payrollChangeHistoryEntity = useAppSelector(state => state.payrollChangeHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="payrollChangeHistoryDetailsHeading">
          <Translate contentKey="capachamaApp.payrollChangeHistory.detail.title">PayrollChangeHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{payrollChangeHistoryEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="capachamaApp.payrollChangeHistory.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {payrollChangeHistoryEntity.date ? (
              <TextFormat value={payrollChangeHistoryEntity.date} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="capachamaApp.payrollChangeHistory.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{payrollChangeHistoryEntity.comments}</dd>
          <dt>
            <Translate contentKey="capachamaApp.payrollChangeHistory.employee">Employee</Translate>
          </dt>
          <dd>{payrollChangeHistoryEntity.employee ? payrollChangeHistoryEntity.employee.id : ''}</dd>
          <dt>
            <Translate contentKey="capachamaApp.payrollChangeHistory.manager">Manager</Translate>
          </dt>
          <dd>{payrollChangeHistoryEntity.manager ? payrollChangeHistoryEntity.manager.id : ''}</dd>
          <dt>
            <Translate contentKey="capachamaApp.payrollChangeHistory.project">Project</Translate>
          </dt>
          <dd>{payrollChangeHistoryEntity.project ? payrollChangeHistoryEntity.project.id : ''}</dd>
          <dt>
            <Translate contentKey="capachamaApp.payrollChangeHistory.changeType">Change Type</Translate>
          </dt>
          <dd>{payrollChangeHistoryEntity.changeType ? payrollChangeHistoryEntity.changeType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payroll-change-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payroll-change-history/${payrollChangeHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PayrollChangeHistoryDetail;
