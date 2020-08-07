import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import GenreUpdatePage from './genre-update.page-object';

const expect = chai.expect;
export class GenreDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.genre.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-genre'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class GenreComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('genre-heading'));
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
    await navBarPage.getEntityPage('genre');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateGenre() {
    await this.createButton.click();
    return new GenreUpdatePage();
  }

  async deleteGenre() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const genreDeleteDialog = new GenreDeleteDialog();
    await waitUntilDisplayed(genreDeleteDialog.deleteModal);
    expect(await genreDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/movieInsightsApp.genre.delete.question/);
    await genreDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(genreDeleteDialog.deleteModal);

    expect(await isVisible(genreDeleteDialog.deleteModal)).to.be.false;
  }
}
