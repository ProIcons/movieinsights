import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ProductionCompanyUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.productionCompany.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#production-company-name'));
  tmdbIdInput: ElementFinder = element(by.css('input#production-company-tmdbId'));
  logoPathInput: ElementFinder = element(by.css('input#production-company-logoPath'));
  originCountryInput: ElementFinder = element(by.css('input#production-company-originCountry'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setTmdbIdInput(tmdbId) {
    await this.tmdbIdInput.sendKeys(tmdbId);
  }

  async getTmdbIdInput() {
    return this.tmdbIdInput.getAttribute('value');
  }

  async setLogoPathInput(logoPath) {
    await this.logoPathInput.sendKeys(logoPath);
  }

  async getLogoPathInput() {
    return this.logoPathInput.getAttribute('value');
  }

  async setOriginCountryInput(originCountry) {
    await this.originCountryInput.sendKeys(originCountry);
  }

  async getOriginCountryInput() {
    return this.originCountryInput.getAttribute('value');
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
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTmdbIdInput('5');
    expect(await this.getTmdbIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setLogoPathInput('logoPath');
    expect(await this.getLogoPathInput()).to.match(/logoPath/);
    await waitUntilDisplayed(this.saveButton);
    await this.setOriginCountryInput('originCountry');
    expect(await this.getOriginCountryInput()).to.match(/originCountry/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
