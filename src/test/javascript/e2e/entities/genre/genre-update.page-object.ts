import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class GenreUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.genre.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tmdbIdInput: ElementFinder = element(by.css('input#genre-tmdbId'));
  nameInput: ElementFinder = element(by.css('input#genre-name'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTmdbIdInput(tmdbId) {
    await this.tmdbIdInput.sendKeys(tmdbId);
  }

  async getTmdbIdInput() {
    return this.tmdbIdInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
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
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
