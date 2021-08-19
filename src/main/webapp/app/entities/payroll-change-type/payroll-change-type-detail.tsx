import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './payroll-change-type.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayrollChangeTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const payrollChangeTypeEntity = useAppSelector(state => state.payrollChangeType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="payrollChangeTypeDetailsHeading">
          <Translate contentKey="capachamaApp.payrollChangeType.detail.title">PayrollChangeType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{payrollChangeTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="capachamaApp.payrollChangeType.name">Name</Translate>
            </span>
          </dt>
          <dd>{payrollChangeTypeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="capachamaApp.payrollChangeType.description">Description</Translate>
            </span>
          </dt>
          <dd>{payrollChangeTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/payroll-change-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payroll-change-type/${payrollChangeTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PayrollChangeTypeDetail;
