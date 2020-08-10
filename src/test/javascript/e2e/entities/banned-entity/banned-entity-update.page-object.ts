import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class BannedEntityUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.bannedEntity.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tmdbIdInput: ElementFinder = element(by.css('input#banned-entity-tmdbId'));
  imdbIdInput: ElementFinder = element(by.css('input#banned-entity-imdbId'));
  typeSelect: ElementFinder = element(by.css('select#banned-entity-type'));
  reasonSelect: ElementFinder = element(by.css('select#banned-entity-reason'));
  reasonTextInput: ElementFinder = element(by.css('input#banned-entity-reasonText'));
  timestampInput: ElementFinder = element(by.css('input#banned-entity-timestamp'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTmdbIdInput(tmdbId) {
    await this.tmdbIdInput.sendKeys(tmdbId);
  }

  async getTmdbIdInput() {
    return this.tmdbIdInput.getAttribute('value');
  }

  async setImdbIdInput(imdbId) {
    await this.imdbIdInput.sendKeys(imdbId);
  }

  async getImdbIdInput() {
    return this.imdbIdInput.getAttribute('value');
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
  async setReasonSelect(reason) {
    await this.reasonSelect.sendKeys(reason);
  }

  async getReasonSelect() {
    return this.reasonSelect.element(by.css('option:checked')).getText();
  }

  async reasonSelectLastOption() {
    await this.reasonSelect.all(by.tagName('option')).last().click();
  }
  async setReasonTextInput(reasonText) {
    await this.reasonTextInput.sendKeys(reasonText);
  }

  async getReasonTextInput() {
    return this.reasonTextInput.getAttribute('value');
  }

  async setTimestampInput(timestamp) {
    await this.timestampInput.sendKeys(timestamp);
  }

  async getTimestampInput() {
    return this.timestampInput.getAttribute('value');
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
    await this.setImdbIdInput('imdbId');
    expect(await this.getImdbIdInput()).to.match(/imdbId/);
    await waitUntilDisplayed(this.saveButton);
    await this.typeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.reasonSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setReasonTextInput('reasonText');
    expect(await this.getReasonTextInput()).to.match(/reasonText/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTimestampInput('01-01-2001');
    expect(await this.getTimestampInput()).to.eq('2001-01-01');
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
