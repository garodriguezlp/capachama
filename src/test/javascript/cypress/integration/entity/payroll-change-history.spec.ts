import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('PayrollChangeHistory e2e test', () => {
  const payrollChangeHistoryPageUrl = '/payroll-change-history';
  const payrollChangeHistoryPageUrlPattern = new RegExp('/payroll-change-history(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/payroll-change-histories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payroll-change-histories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payroll-change-histories/*').as('deleteEntityRequest');
  });

  it('should load PayrollChangeHistories', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payroll-change-history');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PayrollChangeHistory').should('exist');
    cy.url().should('match', payrollChangeHistoryPageUrlPattern);
  });

  it('should load details PayrollChangeHistory page', function () {
    cy.visit(payrollChangeHistoryPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('payrollChangeHistory');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeHistoryPageUrlPattern);
  });

  it('should load create PayrollChangeHistory page', () => {
    cy.visit(payrollChangeHistoryPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('PayrollChangeHistory');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeHistoryPageUrlPattern);
  });

  it('should load edit PayrollChangeHistory page', function () {
    cy.visit(payrollChangeHistoryPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('PayrollChangeHistory');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeHistoryPageUrlPattern);
  });

  it('should create an instance of PayrollChangeHistory', () => {
    cy.visit(payrollChangeHistoryPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('PayrollChangeHistory');

    cy.get(`[data-cy="startDate"]`).type('2021-08-18T07:21').should('have.value', '2021-08-18T07:21');

    cy.get(`[data-cy="endDate"]`).type('2021-08-18T03:07').should('have.value', '2021-08-18T03:07');

    cy.get(`[data-cy="comments"]`).type('Bedfordshire').should('have.value', 'Bedfordshire');

    cy.setFieldSelectToLastOfEntity('employee');

    cy.setFieldSelectToLastOfEntity('manager');

    cy.setFieldSelectToLastOfEntity('project');

    cy.setFieldSelectToLastOfEntity('changeType');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeHistoryPageUrlPattern);
  });

  it('should delete last instance of PayrollChangeHistory', function () {
    cy.intercept('GET', '/api/payroll-change-histories/*').as('dialogDeleteRequest');
    cy.visit(payrollChangeHistoryPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('payrollChangeHistory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', payrollChangeHistoryPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
