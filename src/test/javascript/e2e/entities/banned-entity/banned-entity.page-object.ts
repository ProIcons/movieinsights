import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import BannedEntityUpdatePage from './banned-entity-update.page-object';

const expect = chai.expect;
export class BannedEntityDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.bannedEntity.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-bannedEntity'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class BannedEntityComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('banned-entity-heading'));
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
    await navBarPage.getEntityPage('banned-entity');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateBannedEntity() {
    await this.createButton.click();
    return new BannedEntityUpdatePage();
  }

  async deleteBannedEntity() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const bannedEntityDeleteDialog = new BannedEntityDeleteDialog();
    await waitUntilDisplayed(bannedEntityDeleteDialog.deleteModal);
    expect(await bannedEntityDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/movieInsightsApp.bannedEntity.delete.question/);
    await bannedEntityDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(bannedEntityDeleteDialog.deleteModal);

    expect(await isVisible(bannedEntityDeleteDialog.deleteModal)).to.be.false;
  }
}
