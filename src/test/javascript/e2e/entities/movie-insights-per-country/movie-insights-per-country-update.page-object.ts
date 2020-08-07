import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MovieInsightsPerCountryUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerCountry.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  movieInsightsSelect: ElementFinder = element(by.css('select#movie-insights-per-country-movieInsights'));
  countrySelect: ElementFinder = element(by.css('select#movie-insights-per-country-country'));

  getPageTitle() {
    return this.pageTitle;
  }

  async movieInsightsSelectLastOption() {
    await this.movieInsightsSelect.all(by.tagName('option')).last().click();
  }

  async movieInsightsSelectOption(option) {
    await this.movieInsightsSelect.sendKeys(option);
  }

  getMovieInsightsSelect() {
    return this.movieInsightsSelect;
  }

  async getMovieInsightsSelectedOption() {
    return this.movieInsightsSelect.element(by.css('option:checked')).getText();
  }

  async countrySelectLastOption() {
    await this.countrySelect.all(by.tagName('option')).last().click();
  }

  async countrySelectOption(option) {
    await this.countrySelect.sendKeys(option);
  }

  getCountrySelect() {
    return this.countrySelect;
  }

  async getCountrySelectedOption() {
    return this.countrySelect.element(by.css('option:checked')).getText();
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
    await this.movieInsightsSelectLastOption();
    await this.countrySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
