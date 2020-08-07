import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class BannedEntityUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.bannedEntity.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tmdbIdInput: ElementFinder = element(by.css('input#banned-entity-tmdbId'));
  typeSelect: ElementFinder = element(by.css('select#banned-entity-type'));
  bannedPersistentEntitySelect: ElementFinder = element(by.css('select#banned-entity-bannedPersistentEntity'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTmdbIdInput(tmdbId) {
    await this.tmdbIdInput.sendKeys(tmdbId);
  }

  async getTmdbIdInput() {
    return this.tmdbIdInput.getAttribute('value');
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption() {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }
  async bannedPersistentEntitySelectLastOption() {
    await this.bannedPersistentEntitySelect.all(by.tagName('option')).last().click();
  }

  async bannedPersistentEntitySelectOption(option) {
    await this.bannedPersistentEntitySelect.sendKeys(option);
  }

  getBannedPersistentEntitySelect() {
    return this.bannedPersistentEntitySelect;
  }

  async getBannedPersistentEntitySelectedOption() {
    return this.bannedPersistentEntitySelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setTmdbIdInput('5');
    expect(await this.getTmdbIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.typeSelectLastOption();
    await this.bannedPersistentEntitySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
