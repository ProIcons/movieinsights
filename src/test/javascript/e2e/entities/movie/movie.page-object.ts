import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import MovieUpdatePage from './movie-update.page-object';

const expect = chai.expect;
export class MovieDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.movie.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-movie'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class MovieComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('movie-heading'));
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
    await navBarPage.getEntityPage('movie');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateMovie() {
    await this.createButton.click();
    return new MovieUpdatePage();
  }

  async deleteMovie() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const movieDeleteDialog = new MovieDeleteDialog();
    await waitUntilDisplayed(movieDeleteDialog.deleteModal);
    expect(await movieDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/movieInsightsApp.movie.delete.question/);
    await movieDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(movieDeleteDialog.deleteModal);

    expect(await isVisible(movieDeleteDialog.deleteModal)).to.be.false;
  }
}
