import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CreditUpdatePage from './credit-update.page-object';

const expect = chai.expect;
export class CreditDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.credit.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-credit'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CreditComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('credit-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('credit');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCredit() {
    await this.createButton.click();
    return new CreditUpdatePage();
  }

  async deleteCredit() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const creditDeleteDialog = new CreditDeleteDialog();
    await waitUntilDisplayed(creditDeleteDialog.deleteModal);
    expect(await creditDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/movieInsightsApp.credit.delete.question/);
    await creditDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(creditDeleteDialog.deleteModal);

    expect(await isVisible(creditDeleteDialog.deleteModal)).to.be.false;
  }
}
