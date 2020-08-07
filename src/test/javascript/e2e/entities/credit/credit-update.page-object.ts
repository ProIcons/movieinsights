import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CreditUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.credit.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  creditIdInput: ElementFinder = element(by.css('input#credit-creditId'));
  personTmdbIdInput: ElementFinder = element(by.css('input#credit-personTmdbId'));
  movieTmdbIdInput: ElementFinder = element(by.css('input#credit-movieTmdbId'));
  roleSelect: ElementFinder = element(by.css('select#credit-role'));
  movieSelect: ElementFinder = element(by.css('select#credit-movie'));
  personSelect: ElementFinder = element(by.css('select#credit-person'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCreditIdInput(creditId) {
    await this.creditIdInput.sendKeys(creditId);
  }

  async getCreditIdInput() {
    return this.creditIdInput.getAttribute('value');
  }

  async setPersonTmdbIdInput(personTmdbId) {
    await this.personTmdbIdInput.sendKeys(personTmdbId);
  }

  async getPersonTmdbIdInput() {
    return this.personTmdbIdInput.getAttribute('value');
  }

  async setMovieTmdbIdInput(movieTmdbId) {
    await this.movieTmdbIdInput.sendKeys(movieTmdbId);
  }

  async getMovieTmdbIdInput() {
    return this.movieTmdbIdInput.getAttribute('value');
  }

  async setRoleSelect(role) {
    await this.roleSelect.sendKeys(role);
  }

  async getRoleSelect() {
    return this.roleSelect.element(by.css('option:checked')).getText();
  }

  async roleSelectLastOption() {
    await this.roleSelect.all(by.tagName('option')).last().click();
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
    await this.setCreditIdInput('creditId');
    expect(await this.getCreditIdInput()).to.match(/creditId/);
    await waitUntilDisplayed(this.saveButton);
    await this.setPersonTmdbIdInput('5');
    expect(await this.getPersonTmdbIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMovieTmdbIdInput('5');
    expect(await this.getMovieTmdbIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.roleSelectLastOption();
    await this.movieSelectLastOption();
    await this.personSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
