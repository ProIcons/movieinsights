import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import BannedPersistentEntityUpdatePage from './banned-persistent-entity-update.page-object';

const expect = chai.expect;
export class BannedPersistentEntityDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('movieInsightsApp.bannedPersistentEntity.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-bannedPersistentEntity'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class BannedPersistentEntityComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('banned-persistent-entity-heading'));
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
    await navBarPage.getEntityPage('banned-persistent-entity');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateBannedPersistentEntity() {
    await this.createButton.click();
    return new BannedPersistentEntityUpdatePage();
  }

  async deleteBannedPersistentEntity() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const bannedPersistentEntityDeleteDialog = new BannedPersistentEntityDeleteDialog();
    await waitUntilDisplayed(bannedPersistentEntityDeleteDialog.deleteModal);
    expect(await bannedPersistentEntityDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /movieInsightsApp.bannedPersistentEntity.delete.question/
    );
    await bannedPersistentEntityDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(bannedPersistentEntityDeleteDialog.deleteModal);

    expect(await isVisible(bannedPersistentEntityDeleteDialog.deleteModal)).to.be.false;
  }
}
