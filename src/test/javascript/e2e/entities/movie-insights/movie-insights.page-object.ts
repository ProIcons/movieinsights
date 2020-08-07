import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import MovieInsightsUpdatePage from './movie-insights-update.page-object';

const expect = chai.expect;
export class MovieInsightsDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsights.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-movieInsights'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class MovieInsightsComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('movie-insights-heading'));
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
    await navBarPage.getEntityPage('movie-insights');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateMovieInsights() {
    await this.createButton.click();
    return new MovieInsightsUpdatePage();
  }

  async deleteMovieInsights() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const movieInsightsDeleteDialog = new MovieInsightsDeleteDialog();
    await waitUntilDisplayed(movieInsightsDeleteDialog.deleteModal);
    expect(await movieInsightsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/movieInsightsApp.movieInsights.delete.question/);
    await movieInsightsDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(movieInsightsDeleteDialog.deleteModal);

    expect(await isVisible(movieInsightsDeleteDialog.deleteModal)).to.be.false;
  }
}
