import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ProductionCountryUpdatePage from './production-country-update.page-object';

const expect = chai.expect;
export class ProductionCountryDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.productionCountry.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-productionCountry'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ProductionCountryComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('production-country-heading'));
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
    await navBarPage.getEntityPage('production-country');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateProductionCountry() {
    await this.createButton.click();
    return new ProductionCountryUpdatePage();
  }

  async deleteProductionCountry() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const productionCountryDeleteDialog = new ProductionCountryDeleteDialog();
    await waitUntilDisplayed(productionCountryDeleteDialog.deleteModal);
    expect(await productionCountryDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /movieInsightsApp.productionCountry.delete.question/
    );
    await productionCountryDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(productionCountryDeleteDialog.deleteModal);

    expect(await isVisible(productionCountryDeleteDialog.deleteModal)).to.be.false;
  }
}
