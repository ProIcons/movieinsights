import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class PersonUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.person.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tmdbIdInput: ElementFinder = element(by.css('input#person-tmdbId'));
  nameInput: ElementFinder = element(by.css('input#person-name'));
  imdbIdInput: ElementFinder = element(by.css('input#person-imdbId'));
  popularityInput: ElementFinder = element(by.css('input#person-popularity'));
  biographyInput: ElementFinder = element(by.css('textarea#person-biography'));
  birthDayInput: ElementFinder = element(by.css('input#person-birthDay'));
  profilePathInput: ElementFinder = element(by.css('input#person-profilePath'));

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

  async setImdbIdInput(imdbId) {
    await this.imdbIdInput.sendKeys(imdbId);
  }

  async getImdbIdInput() {
    return this.imdbIdInput.getAttribute('value');
  }

  async setPopularityInput(popularity) {
    await this.popularityInput.sendKeys(popularity);
  }

  async getPopularityInput() {
    return this.popularityInput.getAttribute('value');
  }

  async setBiographyInput(biography) {
    await this.biographyInput.sendKeys(biography);
  }

  async getBiographyInput() {
    return this.biographyInput.getAttribute('value');
  }

  async setBirthDayInput(birthDay) {
    await this.birthDayInput.sendKeys(birthDay);
  }

  async getBirthDayInput() {
    return this.birthDayInput.getAttribute('value');
  }

  async setProfilePathInput(profilePath) {
    await this.profilePathInput.sendKeys(profilePath);
  }

  async getProfilePathInput() {
    return this.profilePathInput.getAttribute('value');
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
    await waitUntilDisplayed(this.saveButton);
    await this.setImdbIdInput('imdbId');
    expect(await this.getImdbIdInput()).to.match(/imdbId/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPopularityInput('5');
    expect(await this.getPopularityInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setBiographyInput('biography');
    expect(await this.getBiographyInput()).to.match(/biography/);
    await waitUntilDisplayed(this.saveButton);
    await this.setBirthDayInput('01-01-2001');
    expect(await this.getBirthDayInput()).to.eq('2001-01-01');
    await waitUntilDisplayed(this.saveButton);
    await this.setProfilePathInput('profilePath');
    expect(await this.getProfilePathInput()).to.match(/profilePath/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
