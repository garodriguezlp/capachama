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

describe('PayrollChangeType e2e test', () => {
  const payrollChangeTypePageUrl = '/payroll-change-type';
  const payrollChangeTypePageUrlPattern = new RegExp('/payroll-change-type(\\?.*)?$');
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
    cy.intercept('GET', '/api/payroll-change-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payroll-change-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payroll-change-types/*').as('deleteEntityRequest');
  });

  it('should load PayrollChangeTypes', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payroll-change-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PayrollChangeType').should('exist');
    cy.url().should('match', payrollChangeTypePageUrlPattern);
  });

  it('should load details PayrollChangeType page', function () {
    cy.visit(payrollChangeTypePageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('payrollChangeType');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeTypePageUrlPattern);
  });

  it('should load create PayrollChangeType page', () => {
    cy.visit(payrollChangeTypePageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('PayrollChangeType');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeTypePageUrlPattern);
  });

  it('should load edit PayrollChangeType page', function () {
    cy.visit(payrollChangeTypePageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('PayrollChangeType');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeTypePageUrlPattern);
  });

  it('should create an instance of PayrollChangeType', () => {
    cy.visit(payrollChangeTypePageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('PayrollChangeType');

    cy.get(`[data-cy="name"]`).type('engage core').should('have.value', 'engage core');

    cy.get(`[data-cy="description"]`).type('Phased Tunnel').should('have.value', 'Phased Tunnel');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', payrollChangeTypePageUrlPattern);
  });

  it('should delete last instance of PayrollChangeType', function () {
    cy.intercept('GET', '/api/payroll-change-types/*').as('dialogDeleteRequest');
    cy.visit(payrollChangeTypePageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('payrollChangeType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', payrollChangeTypePageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
