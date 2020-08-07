import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class BannedPersistentEntityUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.bannedPersistentEntity.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  reasonSelect: ElementFinder = element(by.css('select#banned-persistent-entity-reason'));
  reasonTextInput: ElementFinder = element(by.css('input#banned-persistent-entity-reasonText'));
  timestampInput: ElementFinder = element(by.css('input#banned-persistent-entity-timestamp'));
  movieSelect: ElementFinder = element(by.css('select#banned-persistent-entity-movie'));
  personSelect: ElementFinder = element(by.css('select#banned-persistent-entity-person'));
  productionCompanySelect: ElementFinder = element(by.css('select#banned-persistent-entity-productionCompany'));

  getPageTitle() {
    return this.pageTitle;
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

  async movieSelectLastOption() {
    await this.movieSelect.all(by.tagName('option')).last().click();
  }

  async movieSelectOption(option) {
    await this.movieSelect.sendKeys(option);
  }

  getMovieSelect() {
    return this.movieSelect;
  }

  async getMovieSelectedOption() {
    return this.movieSelect.element(by.css('option:checked')).getText();
  }

  async personSelectLastOption() {
    await this.personSelect.all(by.tagName('option')).last().click();
  }

  async personSelectOption(option) {
    await this.personSelect.sendKeys(option);
  }

  getPersonSelect() {
    return this.personSelect;
  }

  async getPersonSelectedOption() {
    return this.personSelect.element(by.css('option:checked')).getText();
  }

  async productionCompanySelectLastOption() {
    await this.productionCompanySelect.all(by.tagName('option')).last().click();
  }

  async productionCompanySelectOption(option) {
    await this.productionCompanySelect.sendKeys(option);
  }

  getProductionCompanySelect() {
    return this.productionCompanySelect;
  }

  async getProductionCompanySelectedOption() {
    return this.productionCompanySelect.element(by.css('option:checked')).getText();
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
    await this.reasonSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setReasonTextInput('reasonText');
    expect(await this.getReasonTextInput()).to.match(/reasonText/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTimestampInput('01-01-2001');
    expect(await this.getTimestampInput()).to.eq('2001-01-01');
    await this.movieSelectLastOption();
    await this.personSelectLastOption();
    await this.productionCompanySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
