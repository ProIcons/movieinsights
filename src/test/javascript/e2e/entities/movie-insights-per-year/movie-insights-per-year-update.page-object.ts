import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MovieInsightsPerYearUpdatePage {
  pageTitle: ElementFinder = element(by.id('movieInsightsApp.movieInsightsPerYear.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  yearInput: ElementFinder = element(by.css('input#movie-insights-per-year-year'));
  movieInsightsSelect: ElementFinder = element(by.css('select#movie-insights-per-year-movieInsights'));
  movieInsightsPerCountrySelect: ElementFinder = element(by.css('select#movie-insights-per-year-movieInsightsPerCountry'));
  movieInsightsPerCompanySelect: ElementFinder = element(by.css('select#movie-insights-per-year-movieInsightsPerCompany'));
  movieInsightsPerPersonSelect: ElementFinder = element(by.css('select#movie-insights-per-year-movieInsightsPerPerson'));
  movieInsightsPerGenreSelect: ElementFinder = element(by.css('select#movie-insights-per-year-movieInsightsPerGenre'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setYearInput(year) {
    await this.yearInput.sendKeys(year);
  }

  async getYearInput() {
    return this.yearInput.getAttribute('value');
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

  async movieInsightsPerCountrySelectLastOption() {
    await this.movieInsightsPerCountrySelect.all(by.tagName('option')).last().click();
  }

  async movieInsightsPerCountrySelectOption(option) {
    await this.movieInsightsPerCountrySelect.sendKeys(option);
  }

  getMovieInsightsPerCountrySelect() {
    return this.movieInsightsPerCountrySelect;
  }

  async getMovieInsightsPerCountrySelectedOption() {
    return this.movieInsightsPerCountrySelect.element(by.css('option:checked')).getText();
  }

  async movieInsightsPerCompanySelectLastOption() {
    await this.movieInsightsPerCompanySelect.all(by.tagName('option')).last().click();
  }

  async movieInsightsPerCompanySelectOption(option) {
    await this.movieInsightsPerCompanySelect.sendKeys(option);
  }

  getMovieInsightsPerCompanySelect() {
    return this.movieInsightsPerCompanySelect;
  }

  async getMovieInsightsPerCompanySelectedOption() {
    return this.movieInsightsPerCompanySelect.element(by.css('option:checked')).getText();
  }

  async movieInsightsPerPersonSelectLastOption() {
    await this.movieInsightsPerPersonSelect.all(by.tagName('option')).last().click();
  }

  async movieInsightsPerPersonSelectOption(option) {
    await this.movieInsightsPerPersonSelect.sendKeys(option);
  }

  getMovieInsightsPerPersonSelect() {
    return this.movieInsightsPerPersonSelect;
  }

  async getMovieInsightsPerPersonSelectedOption() {
    return this.movieInsightsPerPersonSelect.element(by.css('option:checked')).getText();
  }

  async movieInsightsPerGenreSelectLastOption() {
    await this.movieInsightsPerGenreSelect.all(by.tagName('option')).last().click();
  }

  async movieInsightsPerGenreSelectOption(option) {
    await this.movieInsightsPerGenreSelect.sendKeys(option);
  }

  getMovieInsightsPerGenreSelect() {
    return this.movieInsightsPerGenreSelect;
  }

  async getMovieInsightsPerGenreSelectedOption() {
    return this.movieInsightsPerGenreSelect.element(by.css('option:checked')).getText();
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
    await this.setYearInput('5');
    expect(await this.getYearInput()).to.eq('5');
    await this.movieInsightsSelectLastOption();
    await this.movieInsightsPerCountrySelectLastOption();
    await this.movieInsightsPerCompanySelectLastOption();
    await this.movieInsightsPerPersonSelectLastOption();
    await this.movieInsightsPerGenreSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
