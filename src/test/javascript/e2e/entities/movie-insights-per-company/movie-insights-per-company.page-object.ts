import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import MovieInsightsPerCompanyUpdatePage from './movie-insights-per-company-update.page-object';

const expect = chai.expect;
export class MovieInsightsPerCompanyDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerCompany.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-movieInsightsPerCompany'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class MovieInsightsPerCompanyComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('movie-insights-per-company-heading'));
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
    await navBarPage.getEntityPage('movie-insights-per-company');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateMovieInsightsPerCompany() {
    await this.createButton.click();
    return new MovieInsightsPerCompanyUpdatePage();
  }

  async deleteMovieInsightsPerCompany() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const movieInsightsPerCompanyDeleteDialog = new MovieInsightsPerCompanyDeleteDialog();
    await waitUntilDisplayed(movieInsightsPerCompanyDeleteDialog.deleteModal);
    expect(await movieInsightsPerCompanyDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /movieInsightsApp.movieInsightsPerCompany.delete.question/
    );
    await movieInsightsPerCompanyDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(movieInsightsPerCompanyDeleteDialog.deleteModal);

    expect(await isVisible(movieInsightsPerCompanyDeleteDialog.deleteModal)).to.be.false;
  }
}
