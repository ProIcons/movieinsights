import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import MovieInsightsPerGenreUpdatePage from './movie-insights-per-genre-update.page-object';

const expect = chai.expect;
export class MovieInsightsPerGenreDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerGenre.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-movieInsightsPerGenre'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class MovieInsightsPerGenreComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('movie-insights-per-genre-heading'));
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
    await navBarPage.getEntityPage('movie-insights-per-genre');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateMovieInsightsPerGenre() {
    await this.createButton.click();
    return new MovieInsightsPerGenreUpdatePage();
  }

  async deleteMovieInsightsPerGenre() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const movieInsightsPerGenreDeleteDialog = new MovieInsightsPerGenreDeleteDialog();
    await waitUntilDisplayed(movieInsightsPerGenreDeleteDialog.deleteModal);
    expect(await movieInsightsPerGenreDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /movieInsightsApp.movieInsightsPerGenre.delete.question/
    );
    await movieInsightsPerGenreDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(movieInsightsPerGenreDeleteDialog.deleteModal);

    expect(await isVisible(movieInsightsPerGenreDeleteDialog.deleteModal)).to.be.false;
  }
}
