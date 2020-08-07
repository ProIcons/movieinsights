import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import MovieInsightsPerPersonUpdatePage from './movie-insights-per-person-update.page-object';

const expect = chai.expect;
export class MovieInsightsPerPersonDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerPerson.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-movieInsightsPerPerson'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class MovieInsightsPerPersonComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('movie-insights-per-person-heading'));
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
    await navBarPage.getEntityPage('movie-insights-per-person');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateMovieInsightsPerPerson() {
    await this.createButton.click();
    return new MovieInsightsPerPersonUpdatePage();
  }

  async deleteMovieInsightsPerPerson() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const movieInsightsPerPersonDeleteDialog = new MovieInsightsPerPersonDeleteDialog();
    await waitUntilDisplayed(movieInsightsPerPersonDeleteDialog.deleteModal);
    expect(await movieInsightsPerPersonDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /movieInsightsApp.movieInsightsPerPerson.delete.question/
    );
    await movieInsightsPerPersonDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(movieInsightsPerPersonDeleteDialog.deleteModal);

    expect(await isVisible(movieInsightsPerPersonDeleteDialog.deleteModal)).to.be.false;
  }
}
